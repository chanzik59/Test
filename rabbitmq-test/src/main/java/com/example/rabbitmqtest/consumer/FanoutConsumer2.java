package com.example.rabbitmqtest.consumer;

import com.example.rabbitmqtest.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author chenzhiqin
 * @date 2022/7/20 18:06
 * @info XX
 */
@Component
@Slf4j
@RabbitListener(concurrency = "4",ackMode ="MANUAL", bindings = @QueueBinding(value =@Queue(name = "123",durable="false"),exchange = @Exchange(value = RabbitmqConfig.FANOUT_EXCHANGE,type = ExchangeTypes.FANOUT)))
public class FanoutConsumer2 {

    @RabbitHandler(isDefault = true)
    public void handler3(String message, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("测试handler4{},信息编号为{}",message,deliveryTag);
        channel.basicAck(deliveryTag,false);
    }


}
