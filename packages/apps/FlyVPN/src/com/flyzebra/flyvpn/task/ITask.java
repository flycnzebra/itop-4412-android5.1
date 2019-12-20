package com.flyzebra.flyvpn.task;

/**
 * ClassName: ITask
 * Description:
 * Author: FlyZebra
 * Email:flycnzebra@gmail.com
 * Date: 19-12-11 上午9:57
 */
public interface ITask {
    void onCreate();
    void start();
    void stop();
    void onDestory();
}
