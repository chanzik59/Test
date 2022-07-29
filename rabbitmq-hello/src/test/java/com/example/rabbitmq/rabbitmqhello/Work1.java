package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.Channel;

/**
 * @author chenzhiqin
 * @date 2022/7/6 10:40
 * @info XX
 */
public class Work1 {
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
       // channel.basicQos(2);
        System.out.println("c1接收消息");
        channel.basicConsume("123", false, (a, b) -> {
            String message = new String(b.getBody());
          /*  try {
                Thread.sleep(1 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            System.out.println("接收到消息" + message);
            channel.basicAck(b.getEnvelope().getDeliveryTag(),false);


        }, System.out::println);
    }
}
