package com.example.on_java.scheduled;

/**
 * @author chenzhiqin
 * @date 2022/9/29 16:38
 * @info XX
 */
public interface TaskService {
    /**
     * 处理任务
     */
    void handleJob();

    /**
     * 任务id
     *
     * @return
     */
    Integer jobId();

}
