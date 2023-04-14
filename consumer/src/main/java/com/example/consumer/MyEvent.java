package com.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * @author chenzhiqin
 * @date 3/4/2023 10:27
 * @info XX
 */
@Slf4j
public class MyEvent extends ApplicationEvent {

    private String event ;


    public MyEvent(Object source) {
        super(source);
        this.event= (String) source;
    }
}
