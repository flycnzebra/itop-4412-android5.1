package com.flyzebra.flyvpn.task;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;

import com.flyzebra.flyvpn.data.MpcMessage;
import com.flyzebra.flyvpn.utils.MyTools;
import com.flyzebra.utils.FlyLog;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ClassName: HeartBeatTask
 * Description:
 * Author: FlyZebra
 * Email:flycnzebra@gmail.com
 * Date: 19-12-10 上午10:01
 */
public class HeartBeatTask implements ITask,Runnable{
    private RatdSocketTask ratdSocketTask;

    private static final int HEARTBEAT_TIME = 5000;
    private static final HandlerThread mHeartBeatThread = new HandlerThread("HeartBeat_Task");

    static {
        mHeartBeatThread.start();
    }

    private static final Handler mHeartBeatHandler = new Handler(mHeartBeatThread.getLooper());
    private AtomicBoolean isRun = new AtomicBoolean(false);

    public HeartBeatTask(Context context, RatdSocketTask ratdSocketTask){
        this.ratdSocketTask = ratdSocketTask;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void start(){
        if (isRun.get()) {
            FlyLog.e("HeartBeatTask is Running...");
            return;
        }else{
            FlyLog.e("HeartBeatTask start...");
        }
        isRun.set(true);
        mHeartBeatHandler.postDelayed(this, SystemClock.uptimeMillis() % HEARTBEAT_TIME);
    }

    @Override
    public void stop(){
        if(isRun.get()){
            isRun.set(false);
            FlyLog.e("HeartBeatTask stop...");
        }
        mHeartBeatHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestory() {
        mHeartBeatHandler.removeCallbacksAndMessages(null);
        ratdSocketTask = null;
    }


    @Override
    public void run() {
        long curretTime = SystemClock.uptimeMillis() % HEARTBEAT_TIME;
        long delayedTime = curretTime == 0 ? HEARTBEAT_TIME : HEARTBEAT_TIME - curretTime;
        mHeartBeatHandler.postDelayed(this, delayedTime);
        //发送心跳
        if (ratdSocketTask != null) {
            ratdSocketTask.sendMessage(String.format(MpcMessage.heartBeat, MyTools.createSessionId()));
        }
    }
}
