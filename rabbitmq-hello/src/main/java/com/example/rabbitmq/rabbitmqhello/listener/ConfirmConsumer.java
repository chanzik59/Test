package com.example.rabbitmq.rabbitmqhello.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 2022/7/15 17:42
 * @info XX
 */
@Component
@Slf4j
public class ConfirmConsumer {

    @RabbitListener(queues = "confirm.queue")
    public void queueDListener(Message message, Channel channel) {
        String mqMessage = new String(message.getBody());
        log.info("确认收到的消息{}", mqMessage);
    }
}
