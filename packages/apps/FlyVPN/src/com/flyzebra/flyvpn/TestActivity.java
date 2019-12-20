package com.flyzebra.flyvpn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.flyzebra.utils.ByteTools;
import com.flyzebra.utils.FlyLog;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import xinwei.com.mpapp.MainService;
import xinwei.com.mpapp.R;

/**
 * ClassName: TestActivity
 * Description:
 * Author: FlyZebra
 * Email:flycnzebra@gmail.com
 * Date: 19-12-8 上午8:40
 */
public class TestActivity extends Activity {
    private int fd;

    static {
        System.loadLibrary("flyvpn");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moveTaskToBack(true);
        startService(new Intent(this, MainService.class));
    }

    public void openService(View view){
        moveTaskToBack(true);
        startService(new Intent(this, MainService.class));
    }

    public void testUDP(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FlyLog.d("UDP sokcet client is run!");
                    DatagramSocket socket = null;
                    socket = new DatagramSocket();
                    FlyLog.d(socket.toString());
                    socket.setSoTimeout(3000);
                    String ssend = "[{\"messageType\":3,\"netType\":4,\"netTypeName\":\"wlan0\",\"sessionid\":110358574}]";
                    byte send[] = new byte[]{
                            (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x03,
                            (byte)0x06,(byte)0x12,(byte)0x29,(byte)0x87,
                            (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x16,
                            (byte)0x89,(byte)0x95,(byte)0xb6,(byte)0x37,
                            (byte)0xc9,(byte)0xc4,(byte)0x93,(byte)0x38,
                            (byte)0x00,(byte)0x34};
                    DatagramPacket sendpack = new DatagramPacket(send,
                            send.length,
                            InetAddress.getByName("103.5.126.153"),
                            5060);
                    FlyLog.d("send data=%s", ByteTools.bytes2HexString(send));
                    socket.send(sendpack);
                    socket.receive(sendpack);
                    String str = new String(sendpack.getData(), sendpack.getOffset(),sendpack.getLength());
                    FlyLog.d("recvRatdMessage data=%s", ByteTools.bytes2HexString(str.getBytes()));
                    socket.close();
                    FlyLog.d("UDP sokcet client is end!");
                } catch (Exception e) {
                    e.printStackTrace();
                    FlyLog.e(e.toString());
                }
            }
        }).start();
    }

    public void openTun(View view) {
        fd = openTunDev();
    }

    public void closeTun(View view) {
        closeTunDev(fd);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native int openTunDev();

    public native void closeTunDev(int fd);
}