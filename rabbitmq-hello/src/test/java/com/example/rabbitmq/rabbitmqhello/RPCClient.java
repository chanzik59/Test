package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

public class RPCClient {
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.queueDeclare(RabbitMqUtil.QUEUE_NAME, false, false, false, null);
        for (int i = 0; i < 100; i++) {
            String message = String.valueOf(i);
            String correlationId = UUID.randomUUID().toString();
            String replyQueueName = channel.queueDeclare().getQueue();
            ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
            AMQP.BasicProperties build = MessageProperties.PERSISTENT_TEXT_PLAIN.builder().replyTo(replyQueueName).correlationId(correlationId).build();
            channel.basicPublish("", RabbitMqUtil.QUEUE_NAME, build, message.getBytes(StandardCharsets.UTF_8));
            channel.basicConsume(replyQueueName, true, (consumerTag, mqMessage) -> {
                String replyCorrelationId = mqMessage.getProperties().getCorrelationId();
                if (replyCorrelationId.equals(correlationId)) {
                    queue.offer(Thread.currentThread().getName() + new String(mqMessage.getBody()));
                }

            }, consumerTag -> {
            });
            String replyMessage = queue.take();
            System.out.println("收到回复消息:"+replyMessage);
            channel.queueDelete(replyQueueName);

        }
    }


}