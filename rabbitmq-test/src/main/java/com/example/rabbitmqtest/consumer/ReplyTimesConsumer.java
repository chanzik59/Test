package com.example.rabbitmqtest.consumer;

import com.example.rabbitmqtest.config.RabbitMqRpcConfig;
import com.example.rabbitmqtest.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenzhiqin
 * @date 2022/7/27 12:01
 * @info XX
 */

/**
 * 触发重试加死信的两种方式
 * 1 自动ack 加自动重试
 * 2 手动ack 加手动重试
 * <p>
 * <p>
 * <p>
 * 触发死信的四种情况
 * 1 TTL过期
 * 2 达到最大队列长度
 * 3 消息被拒绝
 * 4 达到最大重试次数
 */
@Component
@Slf4j
@RabbitListener(queues = {RabbitMqRpcConfig.DIRECT_QUEUE, RabbitmqConfig.LAZY_QUEUE}, ackMode = "MANUAL")
public class ReplyTimesConsumer {

    AtomicInteger count = new AtomicInteger();

    @RabbitHandler(isDefault = true)
    public void handler(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliverTag) throws Exception {
        String consumerQueue = message.getMessageProperties().getConsumerQueue();
        log.info("接收到消息{},重试次数{},接收队列{}", new String(message.getBody()), count.incrementAndGet(), consumerQueue);

        channel.basicReject(deliverTag,false);

       /* for (int i = 0; i < 10; i++) {
            log.info("接收到消息{},重试次数{},接收队列{}", new String(message.getBody()), count.incrementAndGet(), consumerQueue);
        }
        if (consumerQueue.equals(RabbitmqConfig.LAZY_QUEUE)) {
            channel.basicAck(deliverTag, false);
        } else {
            channel.basicReject(deliverTag, false);
        }*/
    }

}
