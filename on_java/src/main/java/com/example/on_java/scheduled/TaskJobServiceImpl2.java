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
public class TaskJobServiceImpl2 implements TaskService {
    @Override
    public void handleJob() {
        try {
            log.info("执行任务2开始");
            Thread.sleep(1000L * 10);
            log.info("执行任务2结束");
        } catch (InterruptedException e) {
            log.error("中断异常", e);
        }
    }

    @Override
    public Integer jobId() {
        return 2;
    }
}
