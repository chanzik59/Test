package com.example.rabbitmqtest.consumer;

import com.example.rabbitmqtest.config.RabbitMqRpcConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 2022/7/27 12:01
 * @info XX
 */
@Component
@Slf4j
@RabbitListener(queues = RabbitMqRpcConfig.DIRECT_DEAD_QUEUE)
public class DeadConsumer {


    @RabbitHandler(isDefault = true)
    public void handler(@Payload String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliverTag) throws Exception {
        log.info("死信队列接收消息{}", message);
    }

}
