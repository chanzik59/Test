package com.example.on_java.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author chenzhiqin
 * @date 2022/9/29 16:40
 * @info XX
 */
@Service
@Slf4j
public class TaskJobServiceImpl implements TaskService {
    @Override
    public void handleJob() {
        log.info("执行任务1");
    }

    @Override
    public Integer jobId() {
        return 1;
    }
}
