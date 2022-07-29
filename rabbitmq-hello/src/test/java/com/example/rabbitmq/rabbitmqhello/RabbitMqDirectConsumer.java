package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * @author chenzhiqin
 * @date 2022/7/13 9:51
 * @info XX
 */
public class RabbitMqDirectConsumer {
    static  String exchangeName="direct";
    static  String queueName="ab";

    public static void main(String[] args) throws  Exception {
        String aa="aa";
        String s = aa.toLowerCase();
        System.out.println(s);
        /*Channel channel = RabbitMqUtil.getChannel();

        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,exchangeName,"12");
        channel.queueBind(queueName,exchangeName,"13");
        channel.basicConsume(queueName,true,(a,b)->{
            System.out.println("consumer1 "+new String(b.getBody()));
        },(a,b)->{});*/
    }
}
