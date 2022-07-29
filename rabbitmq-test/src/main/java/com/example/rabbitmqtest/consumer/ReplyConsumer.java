package com.example.rabbitmqtest.consumer;

import com.example.rabbitmqtest.config.RabbitMqRpcConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author chenzhiqin
 * @date 2022/7/27 12:01
 * @info XX
 */
@Component
@RabbitListener(queues = RabbitMqRpcConfig.TEMPLATE_QUEUE)
public class ReplyConsumer {

    @RabbitHandler
    public String handler(byte[] message, @Headers Map<String, Object> headers, Channel channel) {
        String stringMessage = new String(message);
        System.out.println("收到消息" + stringMessage);
        System.out.println(headers);
        return "reply" + stringMessage;
    }
}
