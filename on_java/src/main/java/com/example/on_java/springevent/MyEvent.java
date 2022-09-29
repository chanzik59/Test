package com.example.on_java.springevent;

import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @author chenzhiqin
 * @date 2022/9/29 15:32
 * @info XX
 */

@ToString
public class MyEvent extends ApplicationEvent {

    public MyEvent(String name) {
        super(name);
    }
}
