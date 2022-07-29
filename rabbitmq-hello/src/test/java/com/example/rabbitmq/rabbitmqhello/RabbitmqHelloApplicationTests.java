package com.example.rabbitmq.rabbitmqhello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

@SpringBootTest
class RabbitmqHelloApplicationTests {

    @Value("${queue.name}")
    private String queueName;
    @Value("${rabbitmq.host}")
    private String rabbitmqHost;
    @Value("${rabbitmq.username}")
    private String userName;
    @Value("${rabbitmq.password}")
    private String password;
    //消息手动应答是不丢失的，在没有收到ack应答的时候，会把该消息重新入队，再分配

    //队列修改，需要把原队列删除，否则会发生异常

    //队列持久化 ，和消息持久化共同作用才能保证消息在发生中间件宕机的时候消息不丢失，在发送的时候消息持久化只是告诉中间件，需要持久化，不一定能保证持久化，属于异步刷盘

    // 按需分配原则，再消费端设置qos 为1 默认为0轮询模式

    //发布确认  结合上两个持久化，保证消息不丢失， 单个确认   批量确认   异步批量确认   表示生产者在发送消息的时候，要等到中间件把消息持久化成功了再发送下一波消息

    /**
     * 获取信道
     *
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    private Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitmqHost);
        factory.setUsername(userName);
        factory.setPassword(password);
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }

    /**
     * 获取信道
     *
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    private static Channel getStaticChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.75.128");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
//        String queueName = "hello";
//        Channel channel = getStaticChannel();
//        channel.queueDeclare(queueName, true, false, false, null);
//        channel.confirmSelect();
//        String message = "hello world";
//        channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
//        boolean confirm = channel.waitForConfirms();
//        if (confirm) {
//            System.out.println("消息已发送！！");
//        }else {
//            System.out.println("消息发送失败！！");
//        }

            workConsumer();

    }

    //hello 模式，直连队列直接消费
    @Test
    void commonProducer() throws IOException, TimeoutException {
        Channel channel = getChannel();
        //可以直连队列绕过交换机
        //队列名称  是否持久化  是否排他  是否在最后一个连接断开删除  发送参数
        channel.queueDeclare(queueName, true, false, false, null);
        String message = "hello world";
        channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("消息已发送！！");

    }

    @Test
    void commonConsumer() throws IOException, TimeoutException {
        Channel channel = getChannel();
        //队列名称  是否自动应答  接收消息的回调   取消消息的回调
        channel.basicConsume(queueName, true, (a, b) -> System.out.println(new String(b.getBody(), StandardCharsets.UTF_8)), System.out::println);
    }

    /**
     * @see 
     * @throws IOException
     * @throws TimeoutException
     */
    //工作模式， 一条消息只能被处理一次  各个消费线程轮询处理队列中的消息
    static void workConsumer() throws IOException, TimeoutException {
        Channel channel = getStaticChannel();
        System.out.println("工作线程" + Thread.currentThread().getName() + "1");
        channel.basicConsume("hello", false, (a, b) -> {
            System.out.println("工作模式接收消息" + new String(b.getBody(), StandardCharsets.UTF_8));
           // channel.basicAck(b.getEnvelope().getDeliveryTag(), false);
            channel.basicReject(b.getEnvelope().getDeliveryTag(),false);
        //    channel.basicRecover();
        }, v -> System.out.println("工作模式取消消息" + v));
    }

    @Test
    void workProducer() throws IOException, TimeoutException {
        Channel channel = getChannel();
        channel.queueDeclare(queueName, true, false, false, null);
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 50; i++) {
            String message = "消息序号" + i;
            channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送消息" + message);
        }
      /*  while (scanner.hasNext()) {
            String message = scanner.next();

        }*/
    }


}
