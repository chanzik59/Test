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
    public void handleJob()  {
        try {
            String name = Thread.currentThread().getName();
            log.info("执行任务1开始{}",name);
            Thread.sleep(1000L * 6);
            log.info("执行任务1结束{}",name);
        } catch (InterruptedException e) {
           log.error("中断异常",e);
        }
    }

    @Override
    public Integer jobId() {
        return 1;
    }
}
