package com.flyzebra.flyvpn.data;

/**
 * ClassName: MpcStatus
 * Description:
 * Author: FlyZebra
 * Email:flycnzebra@gmail.com
 * Date: 19-12-10 上午9:18
 */
public class MpcStatus {
    /**
     * 线程同步
     */
    public Object lock;
    /**
     * 开启双流
     */
    public Boolean mpcEnable = false;
    /**
     * mcwill链路
     */
    public NetworkLink mcwillLink = new NetworkLink(1);
    /**
     * 4G链路
     */
    public NetworkLink mobileLink = new NetworkLink(2);
    /**
     * Wifi链路
     */
    public NetworkLink wifiLink = new NetworkLink(4);


    private NetworkLink defaultLink = new NetworkLink(-1);


    private MpcStatus() {
    }

    public static MpcStatus getInstance() {
        return MpcStatusHolder.sInstance;
    }

    public NetworkLink getNetLink(int type) {
        switch (type) {
            case 1:
                return mcwillLink;
            case 2:
                return mobileLink;
            case 4:
                return wifiLink;
            default:
                return defaultLink;
        }
    }

    private static class MpcStatusHolder {
        private static final MpcStatus sInstance = new MpcStatus();
    }

    public void disbleAllLink() {
        mcwillLink.isLink = false;
        mobileLink.isLink = false;
        wifiLink.isLink = false;
    }

}
