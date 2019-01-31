package com.app.JMS.bean;

import lombok.Data;

/**
 * Created by ownlove on 2019/1/31.
 */
@Data
public class MsgBody implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private MsgType localMsgType;
}
