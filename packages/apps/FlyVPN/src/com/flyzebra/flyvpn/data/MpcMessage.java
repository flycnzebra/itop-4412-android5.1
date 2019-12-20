package com.flyzebra.flyvpn.data;

/**
 *
 * ClassName: MpcMessage
 * Description:
 * Author: FlyZebra
 * Email:flycnzebra@gmail.com
 * Date: 19-12-10 上午9:20
 */

public class MpcMessage {

    /**
     * 业务类型
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
     */

    public static final String enableMpc = "[{\"messageType\":17,\"netType\":%d,\"netTypeName\":\"%s\",\"sessionid\":%d}]";
    public static final String disaBleMpc = "[{\"messageType\":19,\"sessionid\":%d}]";
    public static final String addLink = "[{\"messageType\":1,\"netType\":%d,\"netTypeName\":\"%s\",\"ip\":\"%s\",\"token\":0,\"band\":0,\"rtt\":0\",\"sessionid\":%d}]";
    public static final String detectLink = "[{\"messageType\":3,\"netType\":%d,\"netTypeName\":\"%s\",\"sessionid\":%d}]";
    public static final String deleteLink = "[{\"messageType\":5,\"numberType\":%d,\"deleteCause\":%d,\"sessionid\":%d,\"token\":0}]";
    public static final String initMpc = "[{\"messageType\":21,\"uid\":%d,\"dns\":\"%s\",\"mag\":\"%s\",\"sessionid\":%d}]";
    public static final String heartBeat = "[{\"messageType\":23,\"sessionid\":%d}]";
    public static final String switchMpcLog = "[{\"messageType\":25,\"sessionid\":%d,\"operation\":%d}]";

    public static final String socketError= "[{\"messageType\":99}]";
    public static final String socketConnect = "[{\"messageType\":100}]";

    public int messageType = 0;
    public int sessionid;
//    public long uid;
//    public String dns;
//    public String mag;
    public int netType;
//    public String netTypeName;
//    public String ip;
//    public String token;
//    public String band;
//    public String rrt;
    public int exceptionCode = 0;
    public int result = -1;

    public boolean isResultOk(){
        return result==0;
    }

}

