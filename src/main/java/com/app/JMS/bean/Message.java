package com.app.JMS.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by ownlove on 2019/1/28.
 */
@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String uuid;
    private MsgType msgType;
    private MsgBody body;
    private MsgSendStatus sentStatus;
    private String senderId;
    private String targetId;
    private long sentTime;
}
