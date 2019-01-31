package com.app.JMS;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.app.JMS.bean.Message;
import com.util.RabbitMQUtil;
import com.util.StrUtil;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/server/{fromUserId}")
public class JaWebsocket {
    private static final Map<String, WebSocketClient> webSocketMap = Collections.synchronizedMap(new HashMap());
    private static CopyOnWriteArraySet<String> online = new CopyOnWriteArraySet<String>();

    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
    private static int onlineCount = 0;
    public static final int MESSAGE_MAX = 15 * 1000 * 1024; // 15, 000 kb

    /**
     * 连接建立成功调用的方法
     *
     * @param session session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("fromUserId") String fromUserId, Session session) {
        session.setMaxBinaryMessageBufferSize(MESSAGE_MAX);
        WebSocketClient webSocketClient = new WebSocketClient();
        webSocketClient.setClientIp(fromUserId);
        webSocketClient.setSession(session);
        String sessionId = session.getId();
        webSocketClient.setSessionId(sessionId);

        webSocketMap.put(sessionId, webSocketClient);
        online.add(sessionId);
        addOnlineCount(); // 在线数加1
        System.out.println("有新连接" + fromUserId + "加入！当前在线人数为" + getOnlineCount());
        checkMessage(fromUserId,sessionId);
    }

    /**
     * 检查RabbitMQ中有这个from_userId应该接收的消息
     * @param fromUserId
     */
    private void checkMessage(String fromUserId,String sessionId) {
        /**
         * 接收from_userId的消息
         */
        byte[] bytes = RabbitMQUtil.receiveMessage(fromUserId);

        if(bytes != null)
        {
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes,0,bytes.length);

            webSocketMap.get(sessionId).getSession().getAsyncRemote()
                    .sendBinary(byteBuffer);
        }
    }

    /**
     * 连接关闭调用的方法
     *
     * @throws IOException
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        webSocketMap.remove(session.getId());
        online.remove(session.getId());
        session.close();
        subOnlineCount(); // 在线数减1
        System.out.println("有一连接" + session.getId() + "关闭！当前在线人数为" + getOnlineCount());
    }


//    @OnMessage
//    public void binaryMessage(@PathParam("fromUserId") String fromUserId, @PathParam("toUserId") String toUserId,
//                              ByteBuffer msg, Session session) {
@OnMessage
public void binaryMessage(@PathParam("fromUserId") String fromUserId, ByteBuffer msg, Session session) {
        byte b[] = msg.array();
        System.out.println("来自客户端+" + session.getId() + "二进制的消息:" + b.length);
        byte[] bytes = new byte[msg.remaining()];
        msg.get(bytes,0,bytes.length);
        Message message = (Message) StrUtil.toObject(bytes);
//        byte[] bytes = StrUtil.toByteArray(message);
        sendMessage(bytes, message.getTargetId(), msg);
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误" + error.getMessage());
        try {
            online.remove(session.getId());
            session.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 开始封装
     * 目标：在message中调用 sendMessage(String from_userID,String to_userID,String message)
     * 或者sendMessage(String from_userID,String to_userID,Bytebuffer  message)
     */

    /**
     * 转发消息,如果找不到toUserId，就发送bytes到RabbitMQ，找到就不发送
     *
     * @throws IOException
     */
    public void sendMessage(byte[] bytes, String t_userID,ByteBuffer byteBuffer){
        WebSocketClient webSocketClient = null;
        Set<String> set = webSocketMap.keySet();
        int i = 0;
        for (String s : set) {
            WebSocketClient client = webSocketMap.get(s);
            if (t_userID.equals(client.getClientIp())) {
                i = 1;
                webSocketClient = client;
                break;
            }
        }

        if (i == 0) {//没有找到
            System.out.println("send to RabbitMQ");
            RabbitMQUtil.sendMessage(t_userID,bytes);
        } else {
            webSocketClient.getSession().getAsyncRemote().sendObject(byteBuffer);
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        JaWebsocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        JaWebsocket.onlineCount--;
    }
}
