package com.example.server;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author chenzhiqin
 * @date 28/3/2023 16:18
 * @info XX
 */
@SpringBootApplication
@EnableDubbo
public class ServerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ServerApplication.class);


        System.out.println("111");
    }
}
