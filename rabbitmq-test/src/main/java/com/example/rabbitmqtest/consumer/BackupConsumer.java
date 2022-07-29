package com.example.rabbitmqtest.consumer;

import com.example.rabbitmqtest.config.RabbitmqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 2022/7/21 9:50
 * @info XX
 */
@Component
@Slf4j
@RabbitListener(queues = RabbitmqConfig.BACKUP_QUEUE)
public class BackupConsumer {

    @RabbitHandler(isDefault = true)
    public void testDirect(String message) {
        log.info("备份交换机收到消息{}", message);
    }
}
