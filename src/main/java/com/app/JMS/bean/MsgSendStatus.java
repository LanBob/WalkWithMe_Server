package com.app.JMS.bean;

/**
 * Created by ownlove on 2019/1/31.
 */
public enum MsgSendStatus {
    DEFAULT,
    //发送中
    SENDING,
    //发送失败
    FAILED,
    //已发送
    SENT;
}
