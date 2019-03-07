package com.util;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by ownlove on 2019/1/29.
 */
public class RabbitMQUtil {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQUtil.class);
    private static final String IP = "localhost";
    private static final int PORT = 5672;
    private static final String userName = "lusr";
    private static final String passWord = "admin";
    private static ConnectionFactory factory = null;
    private static final String EXCHANGE_NAME = "MyExchange";

//    private static final String IP = "119.29.104.193";
//    private static final int PORT = 5672;
//    private static final String userName = "lusr";
//    private static final String passWord = "lj1158";
//    private static ConnectionFactory factory = null;
//    private static final String EXCHANGE_NAME = "MyExchange";


    private static ConnectionFactory getConnectionFactory() {
        if (factory == null)
            factory = new ConnectionFactory();
        return factory;
    }

    static {
        if (factory == null)
            factory = getConnectionFactory();
    }


    public static Connection getConnection() {
        factory.setHost(IP);
        factory.setPort(PORT);
        factory.setUsername(userName);
        factory.setPassword(passWord);
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * @param userId
     * @param bytes
     */
    public static void sendMessage(String userId, byte[] bytes) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = getConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
            channel.queueDeclare(userId, true, false, false, null);
            /**
             * 指定RoutingKay
             */
            String routingKey = userId;
            channel.queueBind(userId, EXCHANGE_NAME, routingKey);

            //发送消息指定两个东西，一是EXCHANGE_NAME,二是routingKey
            channel.basicPublish(EXCHANGE_NAME, routingKey,
                    MessageProperties.TEXT_PLAIN, bytes);
            logger.info("user:" + userId + "\n send Message length :" + bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (channel != null)
                    channel.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static byte[] receiveMessage(String userId) {
        Connection connection = RabbitMQUtil.getConnection();
        try {
            final Channel channel = connection.createChannel();
            channel.queueDeclare(userId, true, false, false, null);
            GetResponse resp = channel.basicGet(userId, true);
            if (resp != null) {
                logger.info("user " + userId + " recevice message " + new String(resp.getBody()));
                channel.basicAck(resp.getEnvelope().getDeliveryTag(), false);
                return resp.getBody();
            } else {
                logger.info("user " + userId + " recevice no message");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
