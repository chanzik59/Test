package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author chenzhiqin
 * @date 2022/7/26 17:31
 * @info XX
 */
public class ReplyConsumer {
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.basicConsume(RabbitMqUtil.QUEUE_NAME,true,(consumerTag, message) -> {
            AMQP.BasicProperties properties = message.getProperties();
            String mqMessage = new String(message.getBody());
            System.out.println(Thread.currentThread().getName()+"收到消息"+mqMessage);
            String replyMessage="rpc消息:"+mqMessage;
            channel.basicPublish(message.getEnvelope().getExchange(),properties.getReplyTo(),properties,replyMessage.getBytes(StandardCharsets.UTF_8));
        },(consumerTag -> {}));
    }
}
