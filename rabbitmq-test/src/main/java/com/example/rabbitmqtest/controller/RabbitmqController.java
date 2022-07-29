package com.example.rabbitmqtest.controller;

import com.example.rabbitmqtest.config.RabbitMqRpcConfig;
import com.example.rabbitmqtest.config.RabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * @author chenzhiqin
 * @date 2022/7/20 16:35
 * @info XX
 */
@RestController
public class RabbitmqController {

    @Autowired
    @Qualifier("directRabbitTemplate")
    private RabbitTemplate template;

    @Autowired
    private RabbitTemplate directRabbitTemplate;
    @Autowired
    private RabbitTemplate fixRabbitTemplate;
    @Autowired
    private RabbitTemplate temRabbitTemplate;

    @RequestMapping("fanout/{message}")
    public String fanoutSend(@PathVariable String message) {
        template.convertAndSend(RabbitmqConfig.FANOUT_EXCHANGE, "", message);
        return message;
    }

    @RequestMapping("direct/{message}")
    public String directSend(@PathVariable String message) {
        template.convertAndSend("direct", "direct", message, new CorrelationData());
        return message;
    }

    @RequestMapping("direct1/{message}")
    public String direct1Send(@PathVariable String message) {
        template.convertAndSend("direct", "direct33", message, new CorrelationData());
        return message;
    }

    @RequestMapping("reply/direct/{message}")
    public String replyDirect(@PathVariable String message) {
        CorrelationData correlationData = new CorrelationData();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setCorrelationId(correlationData.getId());
        Message mqMessage = new Message(message.getBytes(StandardCharsets.UTF_8), messageProperties);
        Message response = directRabbitTemplate.sendAndReceive(RabbitMqRpcConfig.DIRECT_EXCHANGE, "direct", mqMessage, correlationData);
        if (response.getMessageProperties().getCorrelationId().equals(correlationData.getId())) {
            System.out.println("收到响应消息:" + new String(response.getBody()));
        }

        return message;
    }


    @RequestMapping("reply/fix/{message}")
    public String fixDirect(@PathVariable String message) {
        CorrelationData correlationData = new CorrelationData();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setCorrelationId(correlationData.getId());
        Message mqMessage = new Message(message.getBytes(StandardCharsets.UTF_8), messageProperties);
        Message response = fixRabbitTemplate.sendAndReceive(RabbitMqRpcConfig.FIX_EXCHANGE, "fix", mqMessage, correlationData);
        if (correlationData.getId().equals(response.getMessageProperties().getCorrelationId())) {
            return new String(response.getBody());
        }
        return "获取响应失败:" + correlationData.getId();

    }

    @RequestMapping("reply/tem/{message}")
    public String temDirect(@PathVariable String message) {
        CorrelationData correlationData = new CorrelationData();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setCorrelationId(correlationData.getId());
        Message mqMessage = new Message(message.getBytes(StandardCharsets.UTF_8), messageProperties);
        Message response = temRabbitTemplate.sendAndReceive(RabbitMqRpcConfig.TEMPLATE_EXCHANGE, "template", mqMessage, correlationData);
        if (response.getMessageProperties().getCorrelationId().equals(correlationData.getId())) {
            return "template_reply" + new String(response.getBody());
        }
        return "获取响应失败" + correlationData.getId();
    }

    @RequestMapping("reply/{message}")
    public void reply(@PathVariable String message) {
        int i = 0;
        while (true) {
            template.convertAndSend(RabbitMqRpcConfig.DIRECT_EXCHANGE, "direct", message + i++, new CorrelationData());
        }
    }


    @RequestMapping("lazy/{message}")
    public void lazy(@PathVariable String message) {
        template.convertAndSend(RabbitMqRpcConfig.DIRECT_EXCHANGE, "lazy", message, new CorrelationData());
    }


}
