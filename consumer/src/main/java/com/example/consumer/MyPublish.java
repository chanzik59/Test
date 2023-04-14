package com.example.consumer;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 3/4/2023 10:33
 * @info XX
 */
@Component
public class MyPublish implements ApplicationEventPublisherAware {

    private static ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

        publisher=applicationEventPublisher;
    }


    public static void publish(ApplicationEvent event){

        publisher.publishEvent(event);
    }
}
