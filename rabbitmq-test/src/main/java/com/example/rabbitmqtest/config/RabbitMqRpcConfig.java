package com.example.rabbitmqtest.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzhiqin
 * @date 2022/7/27 11:18
 * @info XX
 */

@Configuration
public class RabbitMqRpcConfig {
    public static final String DIRECT_EXCHANGE = "rpc.direct";
    public static final String FIX_EXCHANGE = "rpc.fix";
    public static final String TEMPLATE_EXCHANGE = "rpc.template";
    public static final String DEAD_DIRECT_EXCHANGE = "rpc.direct.dead";

    public static final String DIRECT_QUEUE = "rpc.direct.queue";
    public static final String DIRECT_DEAD_QUEUE = "rpc.direct.dead.queue";
    public static final String FIX_QUEUE = "rpc.fix.queue";
    public static final String FIX_QUEUE_RESPONSE = "rpc.fix.response.queue";
    public static final String TEMPLATE_QUEUE = "rpc.template.queue";

    @Bean
    public DirectExchange directRpcExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public DirectExchange deadRpcExchange() {
        return new DirectExchange(DEAD_DIRECT_EXCHANGE);
    }

    @Bean
    public Queue deadRpcQueue() {
        return new Queue(DIRECT_DEAD_QUEUE);
    }

    @Bean
    public Binding deadBinding(Queue deadRpcQueue, DirectExchange deadRpcExchange) {
        return BindingBuilder.bind(deadRpcQueue).to(deadRpcExchange).with("dead");
    }


    @Bean
    public Queue directQRpcueue() {
        Map<String, Object> argument = new HashMap<>();
        argument.put("x-dead-letter-exchange", DEAD_DIRECT_EXCHANGE);
        argument.put("x-dead-letter-routing-key", "dead");
        return QueueBuilder.nonDurable(DIRECT_QUEUE).withArguments(argument).build();
    }

    @Bean
    public Binding directRpcBinding(Queue directQRpcueue, DirectExchange directRpcExchange) {
        return BindingBuilder.bind(directQRpcueue).to(directRpcExchange).with("direct");
    }


    /**
     * 直接rpc模式
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate directRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        //队列名称固定
        rabbitTemplate.setReplyAddress("amq.rabbitmq.reply-to");
        rabbitTemplate.setUserCorrelationId(true);
        return rabbitTemplate;
    }


    @Bean
    public DirectExchange fixRpcExchange() {
        return new DirectExchange(FIX_EXCHANGE);
    }

    @Bean
    public Queue fixRpcQueue() {
        return new Queue(FIX_QUEUE);
    }

    @Bean
    public Queue fixRpcResponseQueue() {
        return new Queue(FIX_QUEUE_RESPONSE, false, true, true);
    }

    @Bean
    public Binding fixRpcBinding(Queue fixRpcQueue, DirectExchange fixRpcExchange) {
        return BindingBuilder.bind(fixRpcQueue).to(fixRpcExchange).with("fix");
    }

    /**
     * 固定rpc模式
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate fixRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setReplyAddress(FIX_QUEUE_RESPONSE);
        rabbitTemplate.expectedQueueNames();
        rabbitTemplate.setUserCorrelationId(true);
        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer setContainer(ConnectionFactory connectionFactory, RabbitTemplate fixRabbitTemplate) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(FIX_QUEUE_RESPONSE);
        container.setMessageListener(fixRabbitTemplate);
        return container;
    }


    @Bean
    public DirectExchange templateRpcExchange() {
        return new DirectExchange(TEMPLATE_EXCHANGE);
    }

    @Bean
    public Queue templateRpcQueue() {
        return new Queue(TEMPLATE_QUEUE);
    }

    @Bean
    public Binding templateRpcBinding(Queue templateRpcQueue, DirectExchange templateRpcExchange) {
        return BindingBuilder.bind(templateRpcQueue).to(templateRpcExchange).with("template");
    }

    /**
     * 临时rpc模式
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate temRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setUseTemporaryReplyQueues(true);
        rabbitTemplate.setUserCorrelationId(true);
        return rabbitTemplate;
    }


}
