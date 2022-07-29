package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author chenzhiqin
 * @date 2022/7/11 20:35
 * @info XX
 */
public class RabbitMqAsyn {
    final static String QUEUE_NAME = "123";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.confirmSelect();
        //成功回调   失败回调
        ConcurrentSkipListMap<Long, String> skipListMap = new ConcurrentSkipListMap<>();
        channel.addConfirmListener((a,b)-> {
            if(b){
                ConcurrentNavigableMap<Long, String> confirmMap = skipListMap.headMap(a, true);
                confirmMap.clear();
            }else {
                skipListMap.remove(a);
            }
        },(a,b)-> {
            System.out.println("未确认的消息"+skipListMap.get(a));
        });
        for (int i = 0; i < 100000; i++) {
            String message = "消息是" + i;
            skipListMap.put(channel.getNextPublishSeqNo(),message);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

        }
        System.out.println("消息已经发送完毕");
    }
}
