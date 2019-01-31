package com.test;

import com.rabbitmq.client.*;
import com.util.RabbitMQUtil;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by ownlove on 2019/1/29.
 */
public class RabbitMQTest {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQTest.class);

    private static final String EXCHANGE_NAME = "MyExchange";
    private static final String queueName = "queueName";
    private static final String IP = "localhost";
    private static final int PORT = 5672;
    private static final String userName = "lusr";
    private static final String passWord = "admin";


    @Test
    public void produce(){
        String userId = "1158";
        String message = "success";
        RabbitMQUtil.sendMessage(userId,message.getBytes());
    }

    @Test
    public void consumer(){
        String userId = "555";
        byte [] bytes = RabbitMQUtil.receiveMessage(userId);
//        logger.info("message" + new String(bytes));
    }

    @Test
    public void testProducer1() {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitMQUtil.getConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
            channel.queueDeclare(queueName, true, false, false, null);
            /**
             * 指定RoutingKay
             */
            String routingKey = "myKey";
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

            //发送消息指定两个东西，一是EXCHANGE_NAME,二是routingKey
            String message = "Love LJ";
            channel.basicPublish(EXCHANGE_NAME, routingKey,
                    MessageProperties.TEXT_PLAIN, message.getBytes());
            logger.info("send message " + message);
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

    @Test
    public void testConsumer() {
        Connection connection = RabbitMQUtil.getConnection();
        try {
            final Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, true, false, false, null);
            GetResponse resp = channel.basicGet(queueName, true);
            logger.info("recevice message" + new String(resp.getBody()));
            channel.basicAck(resp.getEnvelope().getDeliveryTag(),false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void testConsumer1(){
        Connection connection = RabbitMQUtil.getConnection();
        try {
            final Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, true, false, false, null);
            channel.basicQos(10);
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    logger.info("receive message : " + new String(body));
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };
            channel.basicConsume(queueName, consumer);
            TimeUnit.SECONDS.sleep(5);
            if (channel != null)
                channel.close();
            if (connection != null)
                connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    @Test
    public void testProducer() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP);
        factory.setPort(PORT);
        factory.setUsername(userName);
        factory.setPassword(passWord);
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
            channel.queueDeclare(queueName, true, false, false, null);
            /**
             * 指定RoutingKay
             */
            String routingKey = "myKey";
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

            //发送消息指定两个东西，一是EXCHANGE_NAME,二是routingKey
            String message = "Love LJ";
            channel.basicPublish(EXCHANGE_NAME, routingKey,
                    MessageProperties.TEXT_PLAIN, message.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
