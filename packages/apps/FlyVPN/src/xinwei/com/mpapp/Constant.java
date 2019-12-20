package xinwei.com.mpapp;

/**
 * Created by JD on 2018/1/4.
 */

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;


/**
 * 定义各种常量
 */
public class Constant {

    public static final String ACTION_MAIN_SERVICE_UPDATE_MAG_ENVIRONMENT = "ACTION_MAIN_SERVICE_UPDATE_MAG_ENVIRONMENT";
    public static final String KEY_MAG_IP = "KEY_MAG_IP";
    public static final String KEY_MAG_DNS = "KEY_MAG_DNS";
    //add octopus chenxiaodan 2019.08.26 Increased activation and idle state
    public static final String SCREEN = "isScreenOn";
    //end
    /**
     * MPC消息开始结束标识
     */
    public static final String MPC_START_FLAG = "[{";
    public static final String MPC_END_FLAG = "}]";

    /**
     * 测试数据
     */
    public static  long default_UID = 888888;
    public static  String default_DNS = "202.106.0.20";
    public static  String default_MAG = "210.12.248.82";
    public static  int default_TOKEN = 666666;

    public static String default_PHONE="";
    public static String default_PASSWORD="";
    public static int default_PORT=5060;
    //是否使用测试模拟mpc
    public static final boolean MPC_TEST_FLAG = false;

    /**
     * 网络链路类型
     */
    public static final int NetworkLink_MCWILL = 1;
    public static final int NetworkLink_WIFI = 4;
    public static final int NetworkLink_4G = 2;
    public static final String ACTION_UPDATE_MP_STATUS_FOR_LINK_MANAGER = "intent.action.UPDATE_MP_STATUS_FOR_LINK_MANAGER";
    public static final String KEY_NETWORK_LINK_4G = "NETWORK_LINK_4G";
    public static final String KEY_NETWORK_LINK_MCWILL = "NETWORK_LINK_MCWILL";
    public static final String KEY_NETWORK_LINK_WIFI = "NETWORK_LINK_WIFI";

    //探测包时间间隔
    public static final int DECETE_TIMEOUT = 5000;
    //add octopus chenxiaodan 2019.08.26 Increased activation and idle state
    public static final int DECETE_TIMEOUT_1min = 1000*60;
    public static final int DECETE_TIMEOUT_5min = 1000*60*5;
    //end
    //消息重发时间间隔
    public static final int RESEND_TIMEOUT = 5000;
    //消息重发次数
    public static final int RESEND_COUNT = 5;
    //心跳时间间隔
    public static final int HEART_TIMEOUT = 5000;
    //socket重连时间间隔
    public static final int RECONNNECT_TIMEOUT = 30000;

    public static final int MSG_CHECK_SOCKET_STATUS = 0;
    public static final int MSG_HEART_TASK = 1;

    //socket retry time
    public static final int SOCKET_FAILED_RETRY_TIME = 3;
    public static final int SOCKET_FAILED_SLEEP_TIME = 2000;

    //网卡链路map
//    public static ConcurrentMap<Integer,NetworkLink> networkLinkMap = new ConcurrentHashMap<Integer,NetworkLink>();

    public static final int CONNECTED = 1;
    public static final int DISCONNECTED = 0;

    /**
     * 业务类型
     */
    public static final int MESSAGE_TYPE_5 = 0x1; //增加子链路           1
    public static final int MESSAGE_TYPE_6 = 0x2; //增加子链路响应       2
    public static final int MESSAGE_TYPE_9 = 0x3; //发送探测包请求       3
    public static final int MESSAGE_TYPE_a = 0x4; //探测包响应           4
    public static final int MESSAGE_TYPE_7 = 0x5; //删除子链路           5
    public static final int MESSAGE_TYPE_8 = 0x6; //删除子链路响应       6
    public static final int MESSAGE_TYPE_15 = 0x7; //释放MP链路请求          7
    public static final int MESSAGE_TYPE_16 = 0x8; //释放MP链路响应      8
    public static final int MESSAGE_TYPE_17 = 0x9; //MP建立链路请求          9
    public static final int MESSAGE_TYPE_18 = 0x0a; //MP建立链路响应     10
    public static final int MESSAGE_TYPE_1 = 0x11; //使能双流                    17
    public static final int MESSAGE_TYPE_2 = 0x12; //使能双流响应                18
    public static final int MESSAGE_TYPE_3 = 0x13; //关闭双流                    19
    public static final int MESSAGE_TYPE_4 = 0x14; //关闭双流响应                20
    public static final int MESSAGE_TYPE_0 = 0x15; //初始化配置          21
    public static final int MESSAGE_TYPE_13 = 0x16; //初始化配置响应     22
    public static final int MESSAGE_TYPE_c = 0x17; //心跳                23
    public static final int MESSAGE_TYPE_14 = 0x18; //心跳响应           24
    public static final int MESSAGE_TYPE_e = 0x19; //日志开关                    25
    public static final int MESSAGE_TYPE_b = 0x1a; //异常状态上报        26
    public static final int MESSAGE_TYPE_d = 0x1b; //流量信息上报        27
    public static final long WAIT_TIME=1000*10;//等待超时时间
    /**
     * 结果码
     */
    public static final int RESULT_CODE_SUCCESS  = 0;
    public static final int RESULT_CODE_FAILED  = -1;

    /**
     * result code for add link
     */
    public static final int ADD_LINK_RESULT_CODE_NORMAL_ERROR    = 1;
    public static final int ADD_LINK_RESULT_CODE_PARAM_ERROR     = 2;
    public static final int ADD_LINK_RESULT_CODE_USER_NOT_EXIST  = 3;
    public static final int ADD_LINK_RESULT_CODE_MP_NOT_START    = 4;
    public static final int ADD_LINK_RESULT_CODE_DHCP_FAIL       = 5;
    public static final int ADD_LINK_RESULT_CODE_NETTY_ERROR     = 6;

    /**
     * result code for detect link
     */
    public static final int DETECT_LINK_RESULT_CODE_NORMAL_ERROR    = 1;
    public static final int DETECT_LINK_RESULT_CODE_PARAM_ERROR     = 2;
    public static final int DETECT_LINK_RESULT_CODE_USER_NOT_EXIST  = 3;
    public static final int DETECT_LINK_RESULT_CODE_MP_NOT_START    = 4;
    //public static final int DETECT_LINK_RESULT_CODE_DHCP_FAIL  = 5;
    //public static final int DETECT_LINK_RESULT_CODE_NETTY_ERROR  = 6;
    public static final int DETECT_LINK_RESULT_CODE_TIME_OUT        = 7;
    public static final int DETECT_LINK_RESULT_CODE_DSN_EXCEPTION   = 8;

    /*
     * delete link cause code
     */
    public static final int DELETE_LINK_CAUSE_NORMAL = 0;
    public static final int DELETE_LINK_CAUSE_DEVICE_EXCEPTION = 1;
    public static final int DELETE_LINK_CAUSE_RELEASE = 2;
    public static final int DELETE_LINK_CAUSE_MAG_TIMEOUT = 3;
    public static final int DELETE_LINK_CAUSE_DETECT_TIMEOUT = 4;

    /**
     * 异常码
     */
    public static final int EXCEPTION_CODE_1  = -1;//当前网卡发送数据异常
    public static final int EXCEPTION_CODE_2  = -2;//定时器超时，要求重新探测链路
    public static final int EXCEPTION_CODE_3  = -3;//虚拟网卡设备异常
    public static final int EXCEPTION_CODE_4 = -4;//，删除子链路，关闭多流
    //add octopus chenxiaodan 2019.08.26 Increased activation and idle state
    public static final int EXCEPTION_CODE_5 = -5;//，激活态
    public static final int EXCEPTION_CODE_6 = -6;//，空闲态
    //end


    /**
     * radio广播
     */
    public static final String ACTION_NOTIFICATION_NET_STANDARD = "cootf.intent.action_MAINTENANCE_NOTIFICATION_NET_STANDARD";
    public static final String ACTION_MPAPP_QUERY_NET_STANDARD = "cootf.intent.action_MPAPP_QUERY_NET_STANDARD";
    public static final String KEY_STANDARD = "standard";

    /**
     * 通知主服务，更新网卡信息
     */
    public static final String ACTION_MAIN_SERVICE_UPDATE_NETWORK = "ACTION_MAIN_SERVICE_UPDATE_NETWORK";
    public static final String KEY_NET_STATUS = "KEY_NET_STATUS"; //网卡状态
    public static final String KEY_NET_TYPE = "KEY_NET_TYPE";//网卡类型

    /**
     * 通知主服务，响应调试UI的操作
     */
    public static final String ACTION_MAIN_SERVICE_RESP_UI_OPT = "ACTION_MAIN_SERVICE_RESP_UI_OPT";
    public static final String KEY_SERVICE_TYPE = "KEY_OPT_NET_TYPE"; //业务类型
    public static final String KEY_OPT_CODE = "KEY_OPT_CODE";//操作码

    /**
     * 通知UI界面，更新链路状态广播
     */
    public static final String ACTION_MAIN_ACTIVITY_UPDATE_NETWORK = "ACTION_MAIN_ACTIVITY_UPDATE_NETWORK";
    /**
     * 通知UI界面，更新多流开关状态
     */
    public static final String ACTION_MAIN_ACTIVITY_UPDATE_DOUBLE_FLAG = "ACTION_MAIN_ACTIVITY_UPDATE_DOUBLE_FLAG";
    /**
     * 通知UI界面，更新多流开关key
     */
    public static final String KEY_UPDATE_DOUBLE_FLAG = "KEY_UPDATE_DOUBLE_FLAG";

    public static final String ACTION_MCWILL_DATA_STATE_CHANGE = "android.net.mcWill.STATE_CHANGE";
    /**
     * 绑定FaultDialogService服务的ACTION
     */
    public static final String ACTION_LINKSERVICE_FAULT_DIAGNOSIS= "com.octopus.linkservice.IFaultDiagnosisService";

    /**
     * 获取文件文件数据，并更新
     */
    public static void updateConfigData() {
        String datas = null;
        try {
            //获取配置文件的地址
            File file = new File(Environment.getExternalStorageDirectory(), "mpapp_config.txt");
            FileInputStream is = new FileInputStream(file);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            datas = new String(buffer, "UTF-8").trim();
            is.close();

            if(datas != null){
                String[] configDatas = datas.split(",");
                if(configDatas.length > 3){
                    String uid = configDatas[0];
                    default_UID = Integer.parseInt(uid);
                    default_DNS = configDatas[1];
                    default_MAG = configDatas[2];
                    String token = configDatas[3];
                    default_TOKEN = Integer.parseInt(token);
                }
            }
        } catch (Exception e) {
        }
    }

}
