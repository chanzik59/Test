package com.example.rabbitmqtest.consumer;

import com.example.rabbitmqtest.config.RabbitmqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 2022/7/20 17:44
 * @info XX
 */
@Component
@Slf4j
@RabbitListener(queues = RabbitmqConfig.FANOUT_QUEUE1)
public class RabbitmqHandler {

    @RabbitHandler
    public void handler1(String message) {
        log.info("测试handler1{}", message);
    }


    @RabbitHandler
    public void handler2(byte[] message) {
        log.info("测试handler2{}", message);
    }
}
