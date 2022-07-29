package com.example.rabbitmqtest.config;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @author chenzhiqin
 * @date 2022/7/20 15:35
 * @info XX
 */
@Configuration
@Slf4j
public class RabbitmqConfig {

    public static final String FANOUT_EXCHANGE = "fanout";
    public static final String FANOUT_QUEUE1 = "fanout.queue1";
    public static final String FANOUT_QUEUE2 = "fanout.queue2";

    public static final String DIRECT_EXCHANGE1 = "direct1";
    public static final String DIRECT_QUEUE = "direct.queue";


    public static final String BACKUP_EXCHANGE = "backup";
    public static final String BACKUP_QUEUE = "backup.queue";

    public static final String LAZY_QUEUE = "lazy.queue";


    @Autowired
    @Qualifier("directRabbitTemplate")
    private RabbitTemplate rabbitTemplate;


    @PostConstruct
    public void setCallBack() {
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息已经发送到交换机{}", correlationData);
            } else {
                log.info("消息发送到交换机失败{},原因是{}", correlationData, cause);
            }
        }));

        rabbitTemplate.setReturnsCallback(returned -> {
            log.info("消息路由失败,路由交换机{},消息内容{},路由键{}，失败原因{}", returned.getExchange(), returned.getMessage(), returned.getRoutingKey(), returned.getReplyText());
        });

    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE1, false, true);
    }


    @Bean
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE);
    }

    @Bean
    public Queue lazyQueue() {
        HashMap<String, Object> argument = new HashMap<>();
        argument.put("x-dead-letter-exchange", RabbitMqRpcConfig.DEAD_DIRECT_EXCHANGE);
        argument.put("x-dead-letter-routing-key", "dead");
        return QueueBuilder.durable(LAZY_QUEUE).lazy().withArguments(argument).build();
    }

    @Bean
    public Binding lazyBinding(Queue lazyQueue, DirectExchange directRpcExchange) {

        return BindingBuilder.bind(lazyQueue).to(directRpcExchange).with("lazy");
    }


    @Bean
    public Queue backupQueue() {
        return new Queue(BACKUP_QUEUE);
    }

    @Bean
    public Binding backupBinding(Queue backupQueue, FanoutExchange backupExchange) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE, false, false, false);
    }

    @Bean
    public Binding directQueueBinding(Queue directQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue).to(directExchange).with("direct1");
    }


    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE).build();
    }

    @Bean
    public Queue fanoutQueue1() {
        return QueueBuilder.nonDurable(FANOUT_QUEUE1).build();
    }

    @Bean
    public Queue fanoutQueue2() {
        return QueueBuilder.nonDurable(FANOUT_QUEUE2).build();
    }

    @Bean
    public Binding queue1Binding(Queue fanoutQueue1, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    @Bean
    public Binding queue2Binding(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }

}
