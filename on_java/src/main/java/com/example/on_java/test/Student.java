package com.example.on_java.test;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @author chenzhiqin
 * @date 2022/10/9 10:00
 * @info XX
 */
@Accessors(chain = true)
@Data
@Slf4j
@Component
public class Student {
    private String name;

    @Async(value = "myTaskExecutor")
    public String getName() {
        log.warn("执行异步操作1");
        return "小明";
    }



    @Async(value = "myTaskExecutor")
    public Future<String> getNameResult() {
        log.warn("执行异步操作2");
        return new AsyncResult<>("小强");
    }
}
