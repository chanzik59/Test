package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.Channel;

/**
 * @author chenzhiqin
 * @date 2022/7/6 10:40
 * @info XX
 */
public class Work2 {
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        System.out.println("c2接收消息");
        channel.basicQos(5);
        channel.basicConsume(RabbitMqUtil.QUEUE_NAME, false, (a, b) -> {
            String message = new String(b.getBody());
            try {
                Thread.sleep(30 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("接收到消息" + message);
            channel.basicAck(b.getEnvelope().getDeliveryTag(),false);


        }, System.out::println);
    }
}
