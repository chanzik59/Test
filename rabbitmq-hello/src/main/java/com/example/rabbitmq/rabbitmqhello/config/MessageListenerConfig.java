package com.example.rabbitmq.rabbitmqhello.config;

import com.example.rabbitmq.rabbitmqhello.listener.DirectReceiver;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenzhiqin
 * @date 2022/7/19 11:04
 * @info XX
 */
@Configuration
public class MessageListenerConfig {
    @Autowired
    private CachingConnectionFactory factory;

    @Autowired
    private DirectReceiver directReceiver;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(directReceiver);
        container.setMessageListener(rabbitTemplate);
        return container;

    }
}
