package com.example.on_java.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;

/**
 * @author chenzhiqin
 * @date 2022/9/29 17:34
 * @info XX
 */
@Component
@Slf4j
public class TaskStater implements CommandLineRunner {
    @Resource
    private MyScheduledConfig myScheduledConfig;


    @Override
    public void run(String... args) throws Exception {
        TaskEntity task1 = new TaskEntity(1, "测试1", "0/5 * * * * ?");
      //  TaskEntity task2 = new TaskEntity(2, "测试2", "0/5 * * * * ?");
        LinkedList<TaskEntity> taskEntities = new LinkedList<>();
        taskEntities.add(task1);
        //taskEntities.add(task2);
        myScheduledConfig.batchRefreshTasks(taskEntities);
    }
}
