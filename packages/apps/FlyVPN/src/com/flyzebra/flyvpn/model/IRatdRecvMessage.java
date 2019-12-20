package com.flyzebra.flyvpn.model;

import com.flyzebra.flyvpn.data.MpcMessage;

/**
 * ClassName: IRatdRecvMessage
 * Description:
 * Author: FlyZebra
 * Email:flycnzebra@gmail.com
 * Date: 19-12-10 上午11:17
 */
public interface IRatdRecvMessage {
    void recvRatdMessage(MpcMessage message);
}
