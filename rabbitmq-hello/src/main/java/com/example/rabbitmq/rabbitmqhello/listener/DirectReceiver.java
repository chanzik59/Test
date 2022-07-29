package com.example.rabbitmq.rabbitmqhello.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenzhiqin
 * @date 2022/7/19 11:14
 * @info XX
 */
@Component
@RabbitListener(queues = {"123"})
@Slf4j
public class DirectReceiver implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {

            log.info("收到的消息是{}", message);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
        }


    }

    @Override
    public void onMessage(Message message) {
        ChannelAwareMessageListener.super.onMessage(message);
    }

    @Override
    public void containerAckMode(AcknowledgeMode mode) {
        ChannelAwareMessageListener.super.containerAckMode(mode);
    }

    @Override
    public void onMessageBatch(List<Message> messages) {
        ChannelAwareMessageListener.super.onMessageBatch(messages);
    }

    @Override
    public void onMessageBatch(List<Message> messages, Channel channel) {
        ChannelAwareMessageListener.super.onMessageBatch(messages, channel);
    }
}
