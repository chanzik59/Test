package com.example.on_java.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chenzhiqin
 * @date 2022/10/9 10:41
 * @info XX
 */
@Configuration
public class MyExecutor {

    @Value("${executor.max-size}")
    private int MAX_SIZE;
    @Value("${executor.core-size}")
    private int CORE_SIZE;
    @Value("${executor.queue-num}")
    private int QUEUE_NUM;
    @Value("${executor.keep-alive}")
    private int KEEP_ALIVE;
    @Value("${executor.name-prefix}")
    private String NAME_PREFIX;


    /**
     * 自定义执行器
     *
     * @return
     */
    @Bean
    public Executor myTaskExecutor() {
        ThreadPoolTaskExecutor myExecutor = new ThreadPoolTaskExecutor();
        myExecutor.setCorePoolSize(CORE_SIZE);
        myExecutor.setMaxPoolSize(MAX_SIZE);
        myExecutor.setQueueCapacity(KEEP_ALIVE);
        myExecutor.setThreadNamePrefix(NAME_PREFIX);
        myExecutor.setKeepAliveSeconds(KEEP_ALIVE);
        myExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        myExecutor.initialize();
        return myExecutor;
    }
}
