package com.example.on_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnJavaApplication.class, args);
    }

}
