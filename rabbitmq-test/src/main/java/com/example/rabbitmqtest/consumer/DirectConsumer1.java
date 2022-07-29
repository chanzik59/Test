package com.example.rabbitmqtest.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 2022/7/21 10:57
 * @info XX
 */
@Component
@Slf4j
public class DirectConsumer1 {
    @RabbitListener(queues = "direct.queue")
    public void testDirect1(Message message) {
        log.info("直接交换机1接收到信息{}", new String(message.getBody()));
    }
}
