package com.flyzebra.flyvpn.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;

import com.flyzebra.flyvpn.data.MpcMessage;
import com.flyzebra.flyvpn.data.MpcStatus;
import com.flyzebra.flyvpn.data.NetworkLink;
import com.flyzebra.flyvpn.task.RatdSocketTask;
import com.flyzebra.flyvpn.utils.MyTools;
import com.flyzebra.flyvpn.utils.SystemPropTools;
import com.flyzebra.utils.FlyLog;

import java.util.List;

/**
 * ClassName: MpcController
 * Description:
 * Author: FlyZebra
 * Email:flycnzebra@gmail.com
 * Date: 19-12-10 上午11:05
 */
public class MpcController {

    public static final HandlerThread mSendMpcThread = new HandlerThread("SendSockThread");

    static {
        mSendMpcThread.start();
    }

    private static final Handler mSendMpcHandler = new Handler(mSendMpcThread.getLooper());
    private RatdSocketTask socketClient;

    private MpcController() {
    }

    public static MpcController getInstance() {
        return MpcController.MpcControllerHolder.sInstance;
    }

    public void stopMpc() {
        mSendMpcHandler.removeCallbacksAndMessages(null);
        mSendMpcHandler.post(new Runnable() {
            @Override
            public void run() {
                boolean flag = false;
                if (socketClient != null) {
                    flag = socketClient.sendMessage(String.format(MpcMessage.disaBleMpc, MyTools.createSessionId()));
                }
                if (!flag) {
                    mSendMpcHandler.postDelayed(this, 2000);
                }
            }
        });
    }

    public void switchMpcLog(final int optcode) {
        mSendMpcHandler.post(new Runnable() {
            @Override
            public void run() {
                boolean flag = false;
                if (socketClient != null) {
                    flag = socketClient.sendMessage(String.format(MpcMessage.switchMpcLog, MyTools.createSessionId(),optcode));
                }
                if (!flag) {
                    mSendMpcHandler.postDelayed(this, 2000);
                }
            }
        });
    }

    private static class MpcControllerHolder {
        public static final MpcController sInstance = new MpcController();
    }

    public void init(RatdSocketTask ratdSocketTask) {
        this.socketClient = ratdSocketTask;
    }

    public void startMpc() {
        mSendMpcHandler.removeCallbacksAndMessages(null);
        mSendMpcHandler.post(new Runnable() {
            @Override
            public void run() {
                boolean flag = false;
                if (socketClient != null) {
                    String mag = SystemPropTools.get("persist.sys.mag.ip","210.12.248.82");;
                    String dns = SystemPropTools.get("persist.sys.mag.dns","202.106.0.20");
                    String strUid = (SystemPropTools.get("persist.radio.mcwill.pid","ffffffff")).replace(".","").trim();
                    while (strUid.equals("ffffffff") || strUid.equals("00000000")){
                        FlyLog.e("read system prop uid error, sleel 1000 millis and try again.");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        strUid = (SystemPropTools.get("persist.radio.mcwill.pid","ffffffff")).replace(".","").trim();
                    }
                    long uid = Long.parseLong(strUid, 16);
                    flag = socketClient.sendMessage(String.format(MpcMessage.initMpc, uid, dns, mag, MyTools.createSessionId()));
                }
                if (!flag) {
                    mSendMpcHandler.postDelayed(this, 5000);
                }
            }
        });
    }

    public void enableMpcDefault(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        mSendMpcHandler.post(new Runnable() {
            @Override
            public void run() {
                boolean flag = false;
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Network networks[] = cm.getAllNetworks();
                    for (Network network : networks) {
                        LinkProperties linkProperties = cm.getLinkProperties(network);
                        if (linkProperties != null) {
                            String iface = linkProperties.getInterfaceName();
                            if (!TextUtils.isEmpty(iface)) {
                                int netType = iface.startsWith("wlan") ? 4 : iface.startsWith("rmnet_data") ? 2 : iface.startsWith("mcwill") ? 1 : -1;
                                if (netType > 0) {
                                    if (socketClient != null) {
                                        flag = socketClient.sendMessage(String.format(MpcMessage.enableMpc, netType, iface, MyTools.createSessionId()));
                                    }
                                }
                            }
                        }
                    }
                }
                if (!flag) {
                    mSendMpcHandler.postDelayed(this, 5000);
                }
            }
        });
    }

    public void enableMpc(final NetworkLink networkLink) {

    }

    public void disableMpc(NetworkLink networkLink) {

    }

    public void addNetworkLink(Context context, final int netType) {
        NetworkLink networkLink = MpcStatus.getInstance().getNetLink(netType);
        if (socketClient == null || networkLink == null || networkLink.isLink) return;
        final ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        mSendMpcHandler.post(new Runnable() {
            @Override
            public void run() {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Network networks[] = cm.getAllNetworks();
                    for (Network network : networks) {
                        LinkProperties linkProperties = cm.getLinkProperties(network);
                        if (linkProperties != null) {
                            String iface = linkProperties.getInterfaceName();
                            if (TextUtils.isEmpty(iface)) continue;
                            int tempNetType = iface.startsWith("wlan") ? 4 : iface.startsWith("rmnet_data") ? 2 : iface.startsWith("mcwill") ? 1 : -1;
                            if (tempNetType == netType) {
                                List<LinkAddress> linkAddress = linkProperties.getLinkAddresses();
                                if (linkAddress != null && !linkAddress.isEmpty()) {
                                    String ip = linkAddress.get(0).toString();
                                    ip = ip.substring(0, ip.indexOf("/"));
                                    if (!TextUtils.isEmpty(ip)) {
                                        socketClient.sendMessage(String.format(MpcMessage.addLink, tempNetType, iface, ip, MyTools.createSessionId()));
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    public void delNetworkLink(final int netType, final int delCause) {
        NetworkLink networkLink = MpcStatus.getInstance().getNetLink(netType);
        if (socketClient == null || networkLink == null || !networkLink.isLink) return;
        mSendMpcHandler.post(new Runnable() {
            @Override
            public void run() {
                if (socketClient != null) {
                    socketClient.sendMessage(String.format(MpcMessage.deleteLink,netType,delCause, MyTools.createSessionId()));
                }
            }
        });
    }

}
