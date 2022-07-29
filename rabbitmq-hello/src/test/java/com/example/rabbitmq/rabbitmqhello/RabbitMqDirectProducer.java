package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author chenzhiqin
 * @date 2022/7/13 9:59
 * @info XX
 */
public class RabbitMqDirectProducer {
    static  String exchangeName="direct";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("input key");
            String key = scanner.nextLine();
            System.out.println("input value");
            String value = scanner.nextLine();
            channel.basicPublish(exchangeName,key,null,value.getBytes(StandardCharsets.UTF_8));
            System.out.println("send message "+key+":"+value);
        }
    }

}
