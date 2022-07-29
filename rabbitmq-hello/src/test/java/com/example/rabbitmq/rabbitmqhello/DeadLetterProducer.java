package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

/**
 * @author chenzhiqin
 * @date 2022/7/13 14:28
 * @info XX
 */
public class DeadLetterProducer {
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();

        channel.exchangeDeclare(DeadLetterConsumer.normalExchange, BuiltinExchangeType.DIRECT);
       // AMQP.BasicProperties build = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 0; i < 10; i++) {
            String message = "发送的消息是第几条" + i;
            channel.basicPublish(DeadLetterConsumer.normalExchange, "11", null, message.getBytes(StandardCharsets.UTF_8));

        }
        System.out.println("消息已经发送完成");
    }
}
