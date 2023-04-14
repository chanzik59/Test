package com.example.consumer;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 3/4/2023 10:29
 * @info XX
 */
@Component
public class MyListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        Object source = event.getSource();
        System.out.println(source);

    }
}
