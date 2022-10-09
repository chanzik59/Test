package com.example.on_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class OnJavaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(OnJavaApplication.class, args);
        System.out.println("111");
    }

}
