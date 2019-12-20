package com.flyzebra.flyvpn;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.flyzebra.flyvpn.data.MpcMessage;
import com.flyzebra.flyvpn.data.MpcStatus;
import com.flyzebra.flyvpn.model.IRatdRecvMessage;
import com.flyzebra.flyvpn.model.MpcController;
import com.flyzebra.flyvpn.task.DetectLinkTask;
import com.flyzebra.flyvpn.task.EnableMpcTask;
import com.flyzebra.flyvpn.task.HeartBeatTask;
import com.flyzebra.flyvpn.task.RatdSocketTask;
import com.flyzebra.flyvpn.utils.MyTools;
import com.flyzebra.flyvpn.utils.SystemPropTools;
import com.flyzebra.utils.FlyLog;

import xinwei.com.mpapp.Constant;

/**
 * ClassName: BaseMainService
 * Description:
 * Author: FlyZebra
 * Email:flycnzebra@gmail.com
 * Date: 19-12-10 上午9:21
 */
public class BaseMainService extends Service implements IRatdRecvMessage {
    protected MpcController mpcController = MpcController.getInstance();
    protected MpcStatus mpcStatus = MpcStatus.getInstance();
    protected RatdSocketTask ratdSocketTask;
    protected HeartBeatTask heartBeatTask;
    protected EnableMpcTask enableMpcTask;
    protected DetectLinkTask detectLinkTask;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FlyLog.e("+++++++++++++++++++++++++++++++++++");
        FlyLog.e("+++++version 1.01---2019.12.20+++++");
        FlyLog.e("+++++++++++++++++++++++++++++++++++");
        FlyLog.d("+++++onCreate, mpapp is start!+++++");
        ratdSocketTask = new RatdSocketTask(getApplicationContext());
        ratdSocketTask.start();
        ratdSocketTask.register(this);
        heartBeatTask = new HeartBeatTask(getApplicationContext(), ratdSocketTask);
        enableMpcTask = new EnableMpcTask(getApplicationContext(), ratdSocketTask);
        detectLinkTask = new DetectLinkTask(getApplicationContext(), ratdSocketTask);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyTools.upLinkManager(this, mpcStatus.wifiLink.isLink, mpcStatus.mobileLink.isLink, mpcStatus.mcwillLink.isLink);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        FlyLog.d("+++++onDestroy, mpApp is Stop!+++++");
        heartBeatTask.onDestory();
        enableMpcTask.onDestory();
        detectLinkTask.onDestory();
        ratdSocketTask.unRegister(this);
        ratdSocketTask.onDestory();
        super.onDestroy();
    }

    @Override
    public void recvRatdMessage(MpcMessage message) {
        switch (message.messageType) {
            case 0x2: //增加子链路响应       2
                mpcStatus.getNetLink(message.netType).isLink = message.isResultOk();
                MyTools.upLinkManager(this, mpcStatus.wifiLink.isLink, mpcStatus.mobileLink.isLink, mpcStatus.mcwillLink.isLink);
                break;
            case 0x4: //探测包响应          4
                if (message.isResultOk()) {
                    mpcController.addNetworkLink(this, message.netType);
                } else if (message.result == 7) {
                    mpcController.delNetworkLink(message.netType, Constant.DELETE_LINK_CAUSE_DETECT_TIMEOUT);
                }
                break;
            case 0x6: //删除子链路响应       6
                //TODO:删除链路需要测试，此返回只打印不成功信息，不成功不更行重试操作
                mpcStatus.getNetLink(message.netType).isLink = false;
                MyTools.upLinkManager(this, mpcStatus.wifiLink.isLink, mpcStatus.mobileLink.isLink, mpcStatus.mcwillLink.isLink);
                break;
            case 0x8: //释放MP链路响应       8
                //TODO:需要确认需求和测试
                break;
            case 0x0a: //MP建立链路响应     10
                //TODO:需要确认需求和测试
                break;
            case 0x12: //使能双流响应       18
                //TODO:使能不成功不会发起探测
                if (message.isResultOk()) {
                    mpcStatus.mpcEnable = true;
                    detectLinkTask.start();
                }
                break;
            case 0x14: //关闭双流响应       20
                //TODO:关闭双流后要怎么响应，需要关闭心跳和探测吗
                mpcStatus.disbleAllLink();
//                heartBeatTask.stop();
//                detectLinkTask.stop();
//                String switch_status = SystemPropTools.get("persist.sys.net.support.multi", "true");
//                if ("true".equals(switch_status)) {
//                    mpcStatus.disbleAllLink(this);
//                    mpcStatus.mpcEnable = true;
//                    mpcController.startMpc();
//                }
                break;
            case 0x16: //初始化配置响应     22
                if (message.isResultOk()) {
                    mpcStatus.disbleAllLink();
                    enableMpcTask.start();
                    MyTools.upLinkManager(this, false, false, false);
                } else {
                    //TODO:是否需要更换MAG
                    tryOpenOrCloseMpc();
                }
                break;
            case 0x18: //心跳响应          24
                //TODO:重启RATD
                break;
            case 0x1a: //异常状态上报       26
                //TODO:-2删除链路还是复位
                if (message.exceptionCode == -2||message.exceptionCode== -1) {
                    FlyLog.e("exceptionCode=%d, delete link netType=%d",message.exceptionCode,message.netType);
                    mpcController.delNetworkLink(message.netType, Constant.DELETE_LINK_CAUSE_DEVICE_EXCEPTION);
                    mpcStatus.getNetLink(message.netType).isLink = false;
//                    mpcController.stopMpc();
//                    mpcStatus.mpcEnable = false;
                } else if (message.exceptionCode == -3) {
                    String switch_status = SystemPropTools.get("persist.sys.net.support.multi", "true");
                    if ("true".equals(switch_status)) {
                        tryOpenOrCloseMpc();
                    }
                }
                break;
            case 0x1b: //流量信息上报       27
                break;
            case 0x63:
                //跟RATD失去联系,RatdSocketTask自动发起重新连接操作，初始化所有状态，关闭探测
                mpcStatus.mpcEnable = false;
                mpcStatus.disbleAllLink();
                MyTools.upLinkManager(this, mpcStatus.wifiLink.isLink, mpcStatus.mobileLink.isLink, mpcStatus.mcwillLink.isLink);
                break;
            case 0x64:
                //跟RATD建立通信成功
                mpcStatus.mpcEnable = false;
                mpcStatus.disbleAllLink();
                tryOpenOrCloseMpc();
                break;
        }
    }

    public void tryOpenOrCloseMpc() {
        String switch_status = SystemPropTools.get("persist.sys.net.support.multi", "true");
        mpcStatus.disbleAllLink();
        if ("true".equals(switch_status)) {
            FlyLog.e("mpc switch is open,mpapp start run...");
            if (!mpcStatus.mpcEnable) {
                heartBeatTask.start();
                mpcController.startMpc();
                mpcStatus.mpcEnable = true;
            }
        } else {
            FlyLog.e("mpc switch is close,mpapp not running...");
            if (mpcStatus.mpcEnable) {
                heartBeatTask.stop();
                enableMpcTask.stop();
                detectLinkTask.stop();
                mpcController.stopMpc();
                mpcStatus.mpcEnable = false;
            }
        }
        MyTools.upLinkManager(this, mpcStatus.wifiLink.isLink, mpcStatus.mobileLink.isLink, mpcStatus.mcwillLink.isLink);
    }


}