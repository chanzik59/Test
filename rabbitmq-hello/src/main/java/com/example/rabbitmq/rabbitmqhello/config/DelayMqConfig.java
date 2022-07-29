package com.example.rabbitmq.rabbitmqhello.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @author chenzhiqin
 * @date 2022/7/16 19:15
 * @info XX
 */
@Configuration
public class DelayMqConfig {
    public static final String DELAY_EXCHANGE = "delay";
    public static final String DELAY_QUEUE = "delay.queue";


    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> argument = new HashMap<>(1);
        argument.put("x-delayed-type","direct");
        //交换机名称 交换机类型 是否持久化  自动删除  参数
        return new CustomExchange(DELAY_EXCHANGE, "x-delayed-message", true, false, argument);
    }

    @Bean
    public Queue delayedQueue(){
        return  new Queue(DELAY_QUEUE);
    }

    @Bean
    public Binding delayedQueueBinding(Queue delayedQueue,CustomExchange delayedExchange){
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with("delay").noargs();
    }

}
