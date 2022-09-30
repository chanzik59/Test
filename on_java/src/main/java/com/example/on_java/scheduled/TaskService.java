package com.example.on_java.scheduled;

import org.springframework.scheduling.annotation.Async;

/**
 * @author chenzhiqin
 * @date 2022/9/29 16:38
 * @info XX
 */
public interface TaskService {
    /**
     * 处理任务
     */
    @Async
    void handleJob();

    /**
     * 任务id
     *
     * @return
     */
    Integer jobId();

}
