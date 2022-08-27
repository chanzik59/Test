package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author chenzhiqin
 * @date 2022/7/6 10:27
 * @info XX
 */
public class RabbitMqUtil {
    public static final String RABBITMQ_HOST = "192.168.242.128";
    public static final String PASSWORD = "guest";
    public static final String USER_NAME = "guest";
    public static final String QUEUE_NAME = "hello";
    public static final String EXCHANGE_NAME = "log";

    public static Channel getChannel() throws Exception {
        return getConnection().createChannel();
    }

    public static Connection getConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPassword(PASSWORD);
        factory.setUsername(USER_NAME);
        factory.setHost(RABBITMQ_HOST);
        return factory.newConnection();
    }
}
