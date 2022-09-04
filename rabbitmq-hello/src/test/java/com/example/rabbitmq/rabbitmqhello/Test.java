package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @author chenzhiqin
 * @date 2022/7/12 17:20
 * @info XX
 */

public class Test {
    /**
     * @param args
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.basicQos();
        priority();
    }


    public static void priority() throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
      /*  HashMap<String, Object> argument = new HashMap<>(1);
        argument.put("x-max-priority", 10);
        channel.queueDeclare("priority", true, false, false, argument);
        channel.queueBind("priority", "fanout", "");
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().priority(1).build();
        channel.basicPublish("fanout", "", properties, "priority1".getBytes(StandardCharsets.UTF_8));*/
        channel.basicConsume("priority", true, (DeliverCallback) (consumerTag, message) -> {
            System.out.println(new String(message.getBody()));
        }, consumerTag -> {

        });

    }

    /**
     * mandatory  当消息路由到交换机但是没有路由到队列中的时候 触发return 监听器
     * immediate  消息到达队列但是没有消费者，触发  但是现在已经移除， 使用  ttl 和dlx 代替
     *
     * @throws Exception
     */
    public static void testMandatory() throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.basicPublish("rpc.direct", "6667", true, null, "body".getBytes(StandardCharsets.UTF_8));
        channel.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) -> {
            System.out.println("没有路由到队列的消息" + new String(body));
        });
    }


}
