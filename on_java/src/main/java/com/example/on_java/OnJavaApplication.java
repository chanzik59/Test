package com.example.on_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnJavaApplication {

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(){
            String aa="a";

        });
        SpringApplication.run(OnJavaApplication.class, args);
    }

}
