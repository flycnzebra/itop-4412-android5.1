package com.flyzebra.flyvpn.task;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.os.SystemClock;
import android.text.TextUtils;

import com.flyzebra.flyvpn.data.MpcMessage;
import com.flyzebra.flyvpn.data.MpcStatus;
import com.flyzebra.flyvpn.model.IRatdRecvMessage;
import com.flyzebra.flyvpn.utils.MyTools;
import com.flyzebra.utils.FlyLog;

import java.util.concurrent.atomic.AtomicBoolean;

import xinwei.com.mpapp.Constant;

/**
 * ClassName: DetectLinkTask
 * Description:
 * Author: FlyZebra
 * Email:flycnzebra@gmail.com
 * Date: 19-12-10 上午11:14
 */
//TODO:维护链路信息--探测算法(探测算法，获取当前可用网络并发起探测，对比已链接的网络，对已经链接但在当前网络不存在的链接网络进行关闭链接处理  )
public class DetectLinkTask implements ITask, Runnable, IRatdRecvMessage {
    private final ConnectivityManager cm;
    private final PowerManager pm;
    private Context mContext;
    private RatdSocketTask ratdSocketTask;
    private static final int HEARTBEAT_TIME = 5000;
    private static final HandlerThread mDetectLinkThread = new HandlerThread("DetectLinkTask");
    private long lastRunTime = 0;
    private AtomicBoolean isRun = new AtomicBoolean(false);

    private static final int RUN_LIGHT = 5; //激活态亮屏 5秒探测
    private static final int RUN_NOLIGHT = 10; //激活态灭屏 5秒探测
    private static final int NORUN_LIGHT = 60; //空闲态亮屏 60秒探测
    private static final int NORUN_NOLIGHT = 300; //空闲态灭屏 300秒探测
    private int detect_status = RUN_LIGHT;
    private boolean isScreen_on = true;
    private boolean isRatd_run = true;

    private MyReceiver mReceiver;
    private IntentFilter mIntentFilter;

    static {
        mDetectLinkThread.start();
    }

    private static final Handler mDetectLinkHandler = new Handler(mDetectLinkThread.getLooper());
    private MpcStatus mpcStatus = MpcStatus.getInstance();

    public DetectLinkTask(Context context, RatdSocketTask ratdSocketTask) {
        this.mContext = context;
        this.ratdSocketTask = ratdSocketTask;
        cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        onCreate();
    }

    @Override
    public void onCreate() {
        mReceiver = new MyReceiver();
        ratdSocketTask.register(this);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mIntentFilter.addAction(Constant.ACTION_MCWILL_DATA_STATE_CHANGE);
        mIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//        mIntentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//        mIntentFilter.addAction(Intent.ACTION_SCREEN_ON);
//        mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mContext.registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    public void start() {
        if (isRun.get()) {
            FlyLog.e("DetectLinkTask is Running...");
            return;
        } else {
            FlyLog.e("DetectLinkTask start...");
        }
        isRun.set(true);
        mDetectLinkHandler.post(this);
    }

    @Override
    public void run() {
        long curretTime = (SystemClock.uptimeMillis() - 2500) % HEARTBEAT_TIME;
        long delayedTime = curretTime == 0 ? HEARTBEAT_TIME : HEARTBEAT_TIME - curretTime;
        mDetectLinkHandler.postDelayed(this, Math.min(delayedTime, HEARTBEAT_TIME));
        //探测需求，时间间隔5,60,300
        upDetectStatus();
        switch (detect_status) {
            case NORUN_LIGHT:
                if ((SystemClock.uptimeMillis() - lastRunTime) > 55001) {
                    detectAllNetwork();
                } else {
                    FlyLog.d("\"messageType\":3-->status -6, screen on, 60s one detect");
                }
                break;
            case NORUN_NOLIGHT:
                if ((SystemClock.uptimeMillis() - lastRunTime) > 295001) {
                    detectAllNetwork();
                } else {
                    FlyLog.d("\"messageType\":3-->status -6, screen off, 300s one detect");
                }
                break;
            case RUN_LIGHT:
            case RUN_NOLIGHT:
            default:
                detectAllNetwork();
                break;
        }
    }

    @Override
    public void stop() {
        if (isRun.get()) {
            isRun.set(false);
            FlyLog.e("DetectLinkTask stop...");
        }
        mDetectLinkHandler.removeCallbacksAndMessages(null);
    }


    public void onDestory() {
        ratdSocketTask.unRegister(this);
        ratdSocketTask = null;
        mContext.unregisterReceiver(mReceiver);
    }

    /**
     * 检测已经存在的网络并发起定时探测
     */
    private void detectAllNetwork() {
        lastRunTime = SystemClock.uptimeMillis();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = cm.getAllNetworks();
            boolean findWifi = false;
            boolean findMobile = false;
            boolean findMcwill = false;
            //对当前存在的网络发起探测链接
            for (Network network : networks) {
                LinkProperties linkProperties = cm.getLinkProperties(network);
                if (linkProperties != null && ratdSocketTask != null) {
                    String iface = linkProperties.getInterfaceName();
                    if (TextUtils.isEmpty(iface)) continue;
                    switch (iface) {
                        case "wlan0":
                            findWifi = true;
                            break;
                        case "rmnet_data0":
                            findMobile = true;
                            break;
                        case "mcwill":
                            findMcwill = true;
                            break;
                    }
                }
            }

            //对已经加入链接但是没有查找到的网络进行删除链路操作
            if (mpcStatus.wifiLink.isLink && !findWifi) {
                FlyLog.e("find wifi network lost, delete it");
                ratdSocketTask.sendMessage(String.format(MpcMessage.deleteLink, 4, Constant.DELETE_LINK_CAUSE_NORMAL, MyTools.createSessionId()));
            }
            if (mpcStatus.mobileLink.isLink && !findMobile) {
                FlyLog.e("find 4G network lost, delete it");
                ratdSocketTask.sendMessage(String.format(MpcMessage.deleteLink, 2, Constant.DELETE_LINK_CAUSE_NORMAL, MyTools.createSessionId()));
            }
            if (mpcStatus.mcwillLink.isLink && !findMcwill) {
                FlyLog.e("find mcwill network lost, delete it");
                ratdSocketTask.sendMessage(String.format(MpcMessage.deleteLink, 1, Constant.DELETE_LINK_CAUSE_NORMAL, MyTools.createSessionId()));
            }

            //对没有加入链接但是已经查找到的网络进行探测操作
            if (findWifi) {
                ratdSocketTask.sendMessage(String.format(MpcMessage.detectLink, 4, "wlan0", MyTools.createSessionId()));
            }
            if (findMobile) {
                ratdSocketTask.sendMessage(String.format(MpcMessage.detectLink, 2, "rmnet_data0", MyTools.createSessionId()));
            }
            if (findMcwill) {
                ratdSocketTask.sendMessage(String.format(MpcMessage.detectLink, 1, "mcwill", MyTools.createSessionId()));
            }
        }
    }

    /**
     * 当检测到网络变化时立即对新网络发起探测，立即将不存在的网络移出链路
     */
    private void detectChangeNetwork() {
        FlyLog.e("network is change");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = cm.getAllNetworks();
            boolean findWifi = false;
            boolean findMobile = false;
            boolean findMcwill = false;
            //对当前存在的网络发起探测链接
            for (Network network : networks) {
                LinkProperties linkProperties = cm.getLinkProperties(network);
                if (linkProperties != null && ratdSocketTask != null) {
                    String iface = linkProperties.getInterfaceName();
                    if (TextUtils.isEmpty(iface)) continue;
                    switch (iface) {
                        case "wlan0":
                            findWifi = true;
                            break;
                        case "rmnet_data0":
                            findMobile = true;
                            break;
                        case "mcwill":
                            findMcwill = true;
                            break;
                    }
                }
            }
            //对没有加入链接但是已经查找到的网络进行探测操作
            if (!mpcStatus.wifiLink.isLink && findWifi) {
                FlyLog.e("find wifi network add, detect it");
                ratdSocketTask.sendMessage(String.format(MpcMessage.detectLink, 4, "wlan0", MyTools.createSessionId()));
            }
            if (!mpcStatus.mobileLink.isLink && findMobile) {
                FlyLog.e("find 4g network add, detect it");
                ratdSocketTask.sendMessage(String.format(MpcMessage.detectLink, 2, "rmnet_data0", MyTools.createSessionId()));
            }
            if (!mpcStatus.mcwillLink.isLink && findMcwill) {
                FlyLog.e("find mcwill network add, detect it");
                ratdSocketTask.sendMessage(String.format(MpcMessage.detectLink, 1, "mcwill", MyTools.createSessionId()));
            }

            //对已经加入链接但是没有查找到的网络进行删除链路操作
            if (mpcStatus.wifiLink.isLink && !findWifi) {
                FlyLog.e("find wifi network lost, delete it");
                ratdSocketTask.sendMessage(String.format(MpcMessage.deleteLink, 4, Constant.DELETE_LINK_CAUSE_NORMAL, MyTools.createSessionId()));
            }
            if (mpcStatus.mobileLink.isLink && !findMobile) {
                FlyLog.e("find 4G network lost, delete it");
                ratdSocketTask.sendMessage(String.format(MpcMessage.deleteLink, 2, Constant.DELETE_LINK_CAUSE_NORMAL, MyTools.createSessionId()));
            }
            if (mpcStatus.mcwillLink.isLink && !findMcwill) {
                FlyLog.e("find mcwill network lost, delete it");
                ratdSocketTask.sendMessage(String.format(MpcMessage.deleteLink, 1, Constant.DELETE_LINK_CAUSE_NORMAL, MyTools.createSessionId()));
            }
        }
    }


    @Override
    public void recvRatdMessage(MpcMessage message) {
        if (message.messageType == 0x1A) {
            switch (message.exceptionCode) {
                case -5:
                    isRatd_run = true;
                    break;
                case -6:
                    isRatd_run = false;
                    break;
            }
        }
    }

    private Runnable changeNetworkTask = new Runnable() {
        @Override
        public void run() {
            detectChangeNetwork();
        }
    };

    /**
     * 广播接收器
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                FlyLog.d("recv intent " + intent.toUri(0));
            } catch (Exception e) {
                FlyLog.d(e.toString());
            }
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case ConnectivityManager.CONNECTIVITY_ACTION:
                    case Constant.ACTION_MCWILL_DATA_STATE_CHANGE:
                    case WifiManager.WIFI_STATE_CHANGED_ACTION:
//                    case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                        //网络变化立刻发起探测
                        if (isRun.get()) {
                            mDetectLinkHandler.removeCallbacks(changeNetworkTask);
                            mDetectLinkHandler.post(changeNetworkTask);
                        }
                        break;
//                    case Intent.ACTION_SCREEN_ON:
//                        isScreen_on = true;
//                        break;
//                    case Intent.ACTION_SCREEN_OFF:
//                        isScreen_on = false;
//                        break;
                }
            }

        }
    }

    private void upDetectStatus() {
        boolean isOpen = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            isOpen = pm.isInteractive();
        } else {
            isOpen = pm.isScreenOn();
        }
        if (isOpen) {
            detect_status = isRatd_run ? RUN_LIGHT : NORUN_LIGHT;
        } else {
            detect_status = isRatd_run ? RUN_NOLIGHT : NORUN_NOLIGHT;
        }
    }
}
