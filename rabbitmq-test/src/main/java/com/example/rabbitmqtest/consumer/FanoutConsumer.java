package com.example.rabbitmqtest.consumer;

import com.example.rabbitmqtest.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 2022/7/20 17:11
 * @info XX
 */
@Component
@Slf4j
public class FanoutConsumer {
 /*   @RabbitListener(queues = RabbitmqConfig.FANOUT_QUEUE1)
    public void fanout1(Message message, Channel channel) {
        log.info("消费者1收到消息{}", message);
    }*/

    @RabbitListener(queues = RabbitmqConfig.FANOUT_QUEUE2)
    public void fanout2(Message message, Channel channel) {
        log.info("消费者2收到消息{}", message);
    }
}
