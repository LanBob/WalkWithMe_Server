package com.app.JMS;

import com.app.JMS.bean.Message;
import com.app.JMS.bean.MsgSendStatus;
import com.app.JMS.bean.MsgType;
//import com.app.JMS.bean.TextMsgBody;
import com.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.UUID;

/**
 * Created by ownlove on 2019/1/29.
 */
public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    @org.junit.Test
    public void MessageToObject() throws IOException {

//        Message message = new Message();
//        long name = System.currentTimeMillis();
////        System.out.println(name);
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(name + ""));
//        objectOutputStream.writeObject(message);
//        message.setFromUserId("s");
//        message.setToUserId("5");
//        message.setMessage("hus".getBytes());
//        File a = new File("D:\\myfile1.txt");
        byte[] bb = StrUtil.getBytes("D:\\upload\\IMG_20181124_084426.jpg");
//        message.setMessage(bb);
//        byte[] bytes = StrUtil.toByteArray(message);
//        System.out.println(bytes.length);
    }
    @org.junit.Test
    public void ByteArrayToObject() throws IOException {
        final Message mMessgae=getBaseSendMessage(MsgType.TEXT);
//        TextMsgBody mTextMsgBody=new TextMsgBody();
//        mTextMsgBody.setMessage("hello");
//        mMessgae.setBody(mTextMsgBody);
        byte[] bytes = StrUtil.toByteArray(mMessgae);
        logger.info("lenth " + bytes.length);
    }

    private Message getBaseSendMessage(MsgType msgType){
        Message mMessgae=new Message();
        mMessgae.setUuid(UUID.randomUUID()+"");
        mMessgae.setSenderId("88");
        mMessgae.setTargetId("77");
        mMessgae.setSentTime(System.currentTimeMillis());
        mMessgae.setSentStatus(MsgSendStatus.SENDING);
        mMessgae.setMsgType(msgType);
        return mMessgae;
    }
}
