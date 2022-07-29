package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author chenzhiqin
 * @date 2022/7/6 10:32
 * @info XX
 */
public class RabbitProducer {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtil.getChannel();


//        while (scanner.hasNext()) {
        for (int i = 0; i < 10; i++) {
           // String message = scanner.next();
            String replyQueueName = channel.queueDeclare().getQueue();
            String correlationId = UUID.randomUUID().toString();
            AMQP.BasicProperties basicProperties = MessageProperties.PERSISTENT_TEXT_PLAIN.builder().correlationId(correlationId).replyTo(replyQueueName).build();
            Map<String, Object> args1 = new HashMap<>();
            args1.put("x-message-ttl", 60000);
            args1.put("durable", true);
            args1.put("auto_delete", true);
            channel.queueDeclare(RabbitMqUtil.QUEUE_NAME, false, false, false, args1);
            String message=String.valueOf(i);
            channel.basicPublish("", RabbitMqUtil.QUEUE_NAME, basicProperties, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("已发送消息" + message);
            final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
            String s = channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    if (properties.getCorrelationId().equals(correlationId)) {
                        System.out.println(Thread.currentThread().getName() + ":" + new String(body));
                        response.offer(new String(body, "UTF-8"));
                    }
                }
            });
            String result = response.take();
            System.out.println(Thread.currentThread().getName() + " [RpcClient] Result:'" + result + "'");
          //  channel.basicCancel(s);
        }

//        }
    }
}
