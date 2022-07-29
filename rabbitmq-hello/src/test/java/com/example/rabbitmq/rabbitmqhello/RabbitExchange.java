package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author chenzhiqin
 * @date 2022/7/11 21:19
 * @info XX
 */
public class RabbitExchange {
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.exchangeDeclare(RabbitMqUtil.EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String message = scanner.nextLine();
            System.out.println("发送消息" + message);
            channel.basicPublish(RabbitMqUtil.EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
        }
    }
}
