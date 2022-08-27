package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

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
        HashMap<String, Object> arguemnt = new HashMap<>(1);
        arguemnt.put("x-expires", 1000L * 30);
        AMQP.BasicProperties persistentTextPlain = MessageProperties.PERSISTENT_TEXT_PLAIN;
        channel.queueDeclare("1314", false, false, true, arguemnt);
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
