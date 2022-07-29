package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

/**
 * @author chenzhiqin
 * @date 2022/7/11 21:26
 * @info XX
 */
public class FanoutConsumer {

    public static void main(String[] args) throws  Exception {
        Channel channel = RabbitMqUtil.getChannel();
        //交换机名称  ，交换机类型
        channel.exchangeDeclare(RabbitMqUtil.EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        //生产随机队列
        String queue = channel.queueDeclare().getQueue();
        //绑定队列，被绑定交换机， routingKy
        channel.queueBind( queue, RabbitMqUtil.EXCHANGE_NAME, "");
        channel.basicConsume(queue, true, (a, b) -> {
            System.out.println("接收到的消息" + new String(b.getBody(), StandardCharsets.UTF_8));
        }, v -> System.out.println("消息取消" + v));

    }
}
