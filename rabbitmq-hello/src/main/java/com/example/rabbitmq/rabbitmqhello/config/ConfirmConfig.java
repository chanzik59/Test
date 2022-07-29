package com.example.rabbitmq.rabbitmqhello.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author chenzhiqin
 * @date 2022/7/18 11:43
 * @info XX
 */
@Configuration
@Slf4j
public class ConfirmConfig {
    public static final String CONFIRM_EXCHANGE = "confirm";
    public static final String CONFIRM_QUEUE = "confirm.queue";
    public static final String BACKUP_EXCHANGE = "backup";
    public static final String BACKUP_QUEUE = "backup.queue";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void innit() {
        rabbitTemplate.setConfirmCallback((data, ack, reason) -> {
            String mqId = Objects.nonNull(data) ? data.getId() : "";
            if (ack) {
                log.info("消息发送成功{}", mqId);
            } else {
                log.info("消息发送失败{}，原因是{}", mqId, reason);
            }
        });
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.info("消息被回退{},交换机是{},路由键为{},被退回的原因{}", new String(returnedMessage.getMessage().getBody()), returnedMessage.getExchange(), returnedMessage.getRoutingKey(), returnedMessage.getReplyText());
        });
    }


    @Bean
    public DirectExchange confirmExchange() {
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE).durable(true).withArgument("alternate-exchange", BACKUP_EXCHANGE).build();
    }

    @Bean
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE);
    }

    @Bean
    public Queue confirmQueue() {
        return new Queue(CONFIRM_QUEUE);
    }

    @Bean
    public Queue backupQueue() {
        return new Queue(BACKUP_QUEUE);
    }

    @Bean
    public Binding confirmBinding(Queue confirmQueue, DirectExchange confirmExchange) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with("confirm");
    }


    @Bean
    public Binding backupBinding(Queue backupQueue, FanoutExchange backupExchange) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

}
