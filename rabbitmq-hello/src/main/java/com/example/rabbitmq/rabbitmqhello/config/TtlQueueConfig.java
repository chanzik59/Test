package com.example.rabbitmq.rabbitmqhello.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzhiqin
 * @date 2022/7/15 17:18
 * @info XX
 */
@Configuration
public class TtlQueueConfig {

    public final static String X_EXCHANGE = "X";
    public final static String X_QUEUE_A = "QA";
    public final static String X_QUEUE_B = "QB";
    public final static String Y_EXCHANGE = "Y";
    public final static String Y_QUEUE_D = "QD";
    public final static String X_QUEUE_C = "QC";

    @Bean
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean
    public DirectExchange yExchange() {
        return new DirectExchange(Y_EXCHANGE);
    }

    @Bean
    public Queue queueA() {
        Map<String, Object> argument = new HashMap<>(3);
        argument.put("x-dead-letter-exchange", Y_EXCHANGE);
        argument.put("x-dead-letter-routing-key", "qd");
        argument.put("x-message-ttl", 10000);
        return QueueBuilder.durable(X_QUEUE_A).withArguments(argument).build();
    }

    @Bean
    public Queue queueC() {
        Map<String, Object> argument = new HashMap<>(2);
        argument.put("x-dead-letter-exchange", Y_EXCHANGE);
        argument.put("x-dead-letter-routing-key", "qd");
        return QueueBuilder.durable(X_QUEUE_C).withArguments(argument).build();
    }


    @Bean
    public Queue queueB() {
        Map<String, Object> argument = new HashMap<>(3);
        argument.put("x-dead-letter-exchange", Y_EXCHANGE);
        argument.put("x-dead-letter-routing-key", "qd");
        argument.put("x-message-ttl", 40000);
        return QueueBuilder.durable(X_QUEUE_B).withArguments(argument).build();
    }

    @Bean
    public Queue queueD() {
        return new Queue(Y_QUEUE_D);
    }

    @Bean
    public Binding queueABinding(Queue queueA, DirectExchange xExchange) {
        return BindingBuilder.bind(queueA).to(xExchange).with("qa");

    }

    @Bean
    public Binding queueCBinding(Queue queueC, DirectExchange xExchange) {
        return BindingBuilder.bind(queueC).to(xExchange).with("qc");

    }

    @Bean
    public Binding queueBBinding(Queue queueB, DirectExchange xExchange) {
        return BindingBuilder.bind(queueB).to(xExchange).with("qb");

    }

    @Bean
    public Binding queueDBinding(Queue queueD, DirectExchange yExchange) {
        return BindingBuilder.bind(queueD).to(yExchange).with("qd");

    }
}
