package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.Channel;

/**
 * @author chenzhiqin
 * @date 2022/7/13 14:44
 * @info XX
 */
public class DeadLetterConsumer2 {
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.basicConsume(DeadLetterConsumer.deadLetterQueue,true,(a,b)->{
            System.out.println("死信接收消息"+new String(b.getBody()));
        },(a,b)->{});
    }
}
