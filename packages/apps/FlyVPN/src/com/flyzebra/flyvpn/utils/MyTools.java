package com.flyzebra.flyvpn.utils;

import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * ClassName: MyTools
 * Description:
 * Author: FlyZebra
 * Email:flycnzebra@gmail.com
 * Date: 19-12-10 上午9:56
 */
public class MyTools {
    /**
     * 根据当前时间生成SessionID
     * @return
     */
    public static synchronized int createSessionId() {
        StringBuffer sessionid = new StringBuffer();
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmssSSS");
        sessionid.append(formatter.format(new Date()));
        if (sessionid.length() == 8) {
            Random rand = new Random();
            int ram = rand.nextInt(9);
            sessionid.append(ram);
        }
        return Integer.parseInt(sessionid.toString());
    }

    public static void upLinkManager(Context context, boolean wifi, boolean mobile, boolean mcwill) {
        Intent intent = new Intent("intent.action.UPDATE_MP_STATUS_FOR_LINK_MANAGER");
        intent.putExtra("NETWORK_LINK_WIFI", wifi ? 1 : 0);
        intent.putExtra("NETWORK_LINK_4G", mobile ? 1 : 0);
        intent.putExtra("NETWORK_LINK_MCWILL", mcwill ? 1 : 0);
        context.sendBroadcast(intent);
    }

}
