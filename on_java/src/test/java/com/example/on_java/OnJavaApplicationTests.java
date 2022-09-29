package com.example.on_java;

import com.example.on_java.springevent.EventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class OnJavaApplicationTests {

    @Resource
    private EventPublisher eventPublisher;

    @Test
    void contextLoads() {
        eventPublisher.register("小强");

    }

}
