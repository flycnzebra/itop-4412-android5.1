package com.flyzebra.flyvpn.data;

/**
 * ClassName: NetworkLink
 * Description:
 * Author: FlyZebra
 * Email:flycnzebra@gmail.com
 * Date: 19-12-10 上午9:37
 */
public class NetworkLink {
    public String ip = "";
    public String netTypeName = "";
    public int netType =0;
    public String token = "";
    public String rrt = "";
    public String band ="";
    public boolean isLink = false;

    public NetworkLink(int type){
        this.netType = type;
    }

}
