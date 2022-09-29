package com.example.on_java.springevent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * @author chenzhiqin
 * @date 2022/9/29 15:35
 * @info XX
 */
@Service
@Slf4j
public class EventListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        log.info("收到的event内容{}",event.getSource());
    }
}
