package com.example.on_java.springevent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chenzhiqin
 * @date 2022/9/29 15:33
 * @info XX
 */
@Service
public class EventPublisher {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    public void register(String name) {
        applicationEventPublisher.publishEvent(new MyEvent(name));
    }
}
