package com.flyzebra.flyvpn.task;

import android.content.Context;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.flyzebra.flyvpn.data.MpcMessage;
import com.flyzebra.flyvpn.model.IRatdRecvMessage;
import com.flyzebra.flyvpn.model.MpcController;
import com.flyzebra.utils.FlyLog;
import com.flyzebra.utils.GsonTools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ClassName: RatdSocketTask
 * Description:
 * Author: FlyZebra
 * Email:flycnzebra@gmail.com
 * Date: 19-12-10 上午9:00
 */
public class RatdSocketTask implements ITask, Runnable {
    private Thread mThread;
    private final static String SOCKET_NAME = "socket_ratd";
    private final static int R_CONNET_TIME = 2000; //SOCKET断掉重连时间间隔
    private OutputStream mOutputStream;
    private final Object mDaemonLock = new Object();
    private int BUFFER_SIZE = 4096;
    private static final String RATD_TAG = "RatdSocketTask";
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private AtomicBoolean isRun = new AtomicBoolean(false);


    private List<IRatdRecvMessage> onRecvMessageList = new ArrayList<>();

    public void register(IRatdRecvMessage onRecvMessage) {
        onRecvMessageList.add(onRecvMessage);
    }

    public void unRegister(IRatdRecvMessage onRecvMessage) {
        onRecvMessageList.remove(onRecvMessage);
    }

    private void notifyRecvMessage(final String message) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                for (IRatdRecvMessage onRecvMessage : onRecvMessageList) {
                    List<MpcMessage> mpcMessages = GsonTools.json2ListObject(message, MpcMessage.class);
                    if (mpcMessages != null && !mpcMessages.isEmpty()) {
                        onRecvMessage.recvRatdMessage(mpcMessages.get(0));
                    }
                }
            }
        });
    }


    public RatdSocketTask(Context context) {
        MpcController.getInstance().init(this);
    }

    @Override
    public void run() {
        FlyLog.d("RatdSocketTask start! ");
        isRun.set(true);
        while (isRun.get()) {
            try {
                //开始监听ratd并交互
                listenToSocket();
            } catch (Exception e) {
                FlyLog.e("Error in RatdSocketTask: " + e);
                notifyRecvMessage(MpcMessage.socketError);
                SystemClock.sleep(R_CONNET_TIME);
            }
        }
        isRun.set(false);
        FlyLog.e("RatdSocketTask stop...");
    }

    private void listenToSocket() throws Exception {
        LocalSocket socket = null;
        try {
            socket = new LocalSocket();
//            socket.setSoTimeout(6000);
            LocalSocketAddress address = new LocalSocketAddress(SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
            socket.connect(address);
            InputStream inputStream = socket.getInputStream();
            synchronized (mDaemonLock) {
                mOutputStream = socket.getOutputStream();
            }
            notifyRecvMessage(MpcMessage.socketConnect);
            byte[] buffer = new byte[BUFFER_SIZE];
            while (isRun.get()) {
                int count = inputStream.read(buffer, 0, BUFFER_SIZE);
                if (count < 0) {
                    break;
                }
                ByteBuffer byteBuffer = ByteBuffer.allocate(count);
                byteBuffer.put(buffer, 0, count);
                String tempStr = new String(byteBuffer.array(), "UTF-8");
                int start = -1;
                do {
                    start = tempStr.indexOf("}]");
                    if (start != tempStr.length() - 2) {
                        String retStr = tempStr.substring(0, start + 2);
                        tempStr = tempStr.substring(start + 2);
                        notifyRecvMessage(retStr);
                        FlyLog.d("recv mpc:" + retStr);
                    } else {
                        notifyRecvMessage(tempStr);
                        FlyLog.d("recv mpc:" + tempStr);
                        break;
                    }
                } while (start == -1);

            }
        } catch (Exception ex) {
            FlyLog.d("Communications error: " + ex);
            throw ex;
        } finally {
            synchronized (mDaemonLock) {
                if (mOutputStream != null) {
                    try {
                        FlyLog.d("closing stream for " + SOCKET_NAME);
                        mOutputStream.close();
                    } catch (IOException e) {
                        FlyLog.d("Failed closing output stream: " + e);
                    }
                    mOutputStream = null;
                }
            }

            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                FlyLog.d("Failed closing socket: " + ex);
            }
        }
    }

    public boolean sendMessage(final String message) {
        FlyLog.d("send mpc:" + message);
        synchronized (mDaemonLock) {
            if (mOutputStream == null) {
                FlyLog.e("ratd socket error! mOutputStream = null");
                return false;
            } else {
                try {
                    mOutputStream.write(message.getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    FlyLog.d(e.toString());
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void start(){
        if(isRun.get()){
            FlyLog.e("RatdSocketTask is Running...");
            return;
        }
        mThread = new Thread(this, RATD_TAG);
        mThread.setDaemon(true);
        mThread.start();
    }

    @Override
    public void stop() {
        if(isRun.get()){
            isRun.set(false);
        }
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestory() {
        isRun.set(false);
        mHandler.removeCallbacksAndMessages(null);
    }
}