package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzhiqin
 * @date 2022/7/13 14:14
 * @info XX
 */
public class DeadLetterConsumer {
    static String normalExchange = "normal";
    static String normalQueue = "normalQueue";
    static String deadLetter = "dead";
    static String deadLetterQueue = "deadQueue";

    //成为死信的三个原因  TTL  达到队列最大消息长度  消息确认被拒绝

    public static void main(String[] args) throws  Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.exchangeDeclare(normalExchange, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(deadLetter,BuiltinExchangeType.DIRECT);
        channel.queueDeclare(deadLetterQueue,false,false,false,null);
        channel.queueBind(deadLetterQueue,deadLetter,"22");
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange",deadLetter);
        map.put("x-dead-letter-routing-key","22");
        //map.put("x-max-length",6);
        channel.queueDeclare(normalQueue,false,false,false,map);
        channel.queueBind(normalQueue,normalExchange,"11");
        System.out.println("正常消费者准备接收消息....");
        channel.basicConsume(normalQueue,false,(a,b)->{
            System.out.println("正常消费者接收消息"+new String(b.getBody())+b.getEnvelope().isRedeliver());
            channel.basicReject(b.getEnvelope().getDeliveryTag(),false);
        },(a,b)->{});
    }
}
