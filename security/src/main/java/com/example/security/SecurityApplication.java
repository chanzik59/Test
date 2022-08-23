package com.example.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SecurityApplication.class, args);
    }

}
