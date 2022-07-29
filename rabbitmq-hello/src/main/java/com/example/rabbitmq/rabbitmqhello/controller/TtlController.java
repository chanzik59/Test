package com.example.rabbitmq.rabbitmqhello.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenzhiqin
 * @date 2022/7/15 17:36
 * @info XX
 */
@Slf4j
@RestController("message")
public class TtlController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("ttl/{message}")
    public void sendTtl(@PathVariable String message) {
        System.out.println("接收到信息" + message);
        log.info("接收到信息{}", message);
        rabbitTemplate.convertAndSend("X", "qa", "十秒定时消息" + message);
        rabbitTemplate.convertAndSend("X", "qb", "四十秒定时消息" + message);
        log.info("消息已发送{}", message);

    }

    @RequestMapping("ttl/{time}/{message}")
    public String sendTtlAmdTime(@PathVariable String message,@PathVariable long time) {
        rabbitTemplate.convertAndSend("X","qc",message,mqMessage->{mqMessage.getMessageProperties().setExpiration(String.valueOf(time*1000L));return  mqMessage;});
        log.info("消息已发送{},定时TTL时间{}", message,time);
        return message;

    }

    @RequestMapping("delay/{time}/{message}")
    public String sendDelayAmdTime(@PathVariable String message,@PathVariable Integer time) {
        rabbitTemplate.convertAndSend("delay","delay",message,mqMessage->{mqMessage.getMessageProperties().setDelay(time*1000);return  mqMessage;});
        log.info("消息已发送{},定时延时时间{}", message,time);
        return message;

    }


    @RequestMapping("confirm/{message}")
    public String sendConfirm(@PathVariable String message) {
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend("confirm","confirm1",message,correlationData);
        log.info("确认消息已发送{}", message);
        return message;

    }
}
