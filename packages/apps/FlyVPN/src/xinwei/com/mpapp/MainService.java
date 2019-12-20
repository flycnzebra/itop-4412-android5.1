package xinwei.com.mpapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import com.flyzebra.flyvpn.BaseMainService;
import com.flyzebra.flyvpn.utils.MyTools;
import com.flyzebra.flyvpn.utils.SystemPropTools;
import com.flyzebra.utils.FlyLog;

import xinwei.com.mpapp.aidl.IServiceAidl;


/**
 * Created by JD on 2018/1/4.
 */

public class MainService extends BaseMainService {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private MyServiceImpl myService = null;
    private MainReceiver mainReceiver;
    private IntentFilter mIntentFilter;

    @Override
    public void onCreate() {
        super.onCreate();
        FlyLog.d("onCreate");
        myService = new MyServiceImpl();
        mainReceiver = new MainReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Constant.ACTION_MAIN_SERVICE_UPDATE_NETWORK);
        mIntentFilter.addAction(Constant.ACTION_MAIN_SERVICE_RESP_UI_OPT);
        mIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mIntentFilter.addAction(Constant.ACTION_MCWILL_DATA_STATE_CHANGE);
        mIntentFilter.addAction(Constant.ACTION_NOTIFICATION_NET_STANDARD);
        mIntentFilter.addAction(Intent.ACTION_SCREEN_ON);
        mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mainReceiver, mIntentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FlyLog.d("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 广播接收器
     */
    private class MainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals(Constant.ACTION_MAIN_SERVICE_UPDATE_NETWORK)) {
                int netStatus = intent.getIntExtra(Constant.KEY_NET_STATUS, 0);
                int netType = intent.getIntExtra(Constant.KEY_NET_TYPE, 0);

            } else if (action != null && action.equals(Constant.ACTION_MAIN_SERVICE_RESP_UI_OPT)) {
                int optcode = intent.getIntExtra(Constant.KEY_OPT_CODE, 0);
                int serviceType = intent.getIntExtra(Constant.KEY_SERVICE_TYPE, 0);
                switch (serviceType) {
                    case -1:
                        //重新初始化
                        FlyLog.i("linkserver up settings");
//                        tryOpenOrCloseMpc();
                        break;
                    case 1:
                    case 2:
                    case 4:
                        break;
                    case 6:
                        FlyLog.i("linkserver up settings");
//                        tryOpenOrCloseMpc();
                        break;
                    case 7:
                        //日志开关
                        mpcController.switchMpcLog(optcode);
                        break;
                }
            } else if (action != null && action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            } else if (action != null && action.equals(Constant.ACTION_MCWILL_DATA_STATE_CHANGE)) {
            } else if (action != null && action.equals(Constant.ACTION_NOTIFICATION_NET_STANDARD)) {
            } else if (action != null && action.equals(Intent.ACTION_SCREEN_ON)) {
            } else if (action != null && action.equals(Intent.ACTION_SCREEN_OFF)) {
            }
        }
    }


    private class MyServiceImpl extends IServiceAidl.Stub {
        /**
         * 打开多流开关
         *
         * @param magip
         * @param magport
         * @param dns
         * @param phone
         * @param pwd
         * @param token
         */
        @Override
        public boolean openMultipleStreams(java.lang.String magip, int magport, java.lang.String dns, int uid, java.lang.String phone, java.lang.String pwd, java.lang.String token) throws android.os.RemoteException {
            FlyLog.i("linkserver openMultipleStreams");
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    MyTools.upLinkManager(MainService.this, mpcStatus.wifiLink.isLink, mpcStatus.mobileLink.isLink, mpcStatus.mcwillLink.isLink);
                    SystemPropTools.set("persist.sys.net.support.multi", "true");
                    if(!mpcStatus.mpcEnable){
                        tryOpenOrCloseMpc();
                    }else{
                        FlyLog.e("mpapp is already running...");
                    }
                }
            });
            return true;
        }

        /**
         * 关闭多流开关
         */
        @Override
        public boolean closeMultipleStreams() throws android.os.RemoteException {
            FlyLog.i("linkserver closeMultipleStreams");
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    MyTools.upLinkManager(MainService.this, mpcStatus.wifiLink.isLink, mpcStatus.mobileLink.isLink, mpcStatus.mcwillLink.isLink);
                    SystemPropTools.set("persist.sys.net.support.multi", "false");
                    if(mpcStatus.mpcEnable){
                        tryOpenOrCloseMpc();
                    }else{
                        FlyLog.e("mpapp is already stop...");
                    }
                }
            });
            return true;
        }

        /**
         * 设置网络状态
         *
         * @param network
         */
        @Override
        public void updateNetwork(java.util.List<xinwei.com.mpapp.aidl.Network> network) throws android.os.RemoteException {
        }
    }


    public IBinder onBind(Intent intent) {
        return myService;
    }

    @Override
    public void onDestroy() {
        FlyLog.d("onDestroy");
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
