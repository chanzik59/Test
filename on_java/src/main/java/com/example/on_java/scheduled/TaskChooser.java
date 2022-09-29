package com.example.on_java.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenzhiqin
 * @date 2022/9/29 16:42
 * @info XX
 */
@Component
@Slf4j
public class TaskChooser implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    private Map<Integer, TaskService> tasks = new ConcurrentHashMap<>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void registerToTasks() {
        Map<String, TaskService> taskMap = applicationContext.getBeansOfType(TaskService.class);
        for (TaskService value : taskMap.values()) {
            tasks.put(value.jobId(), value);
        }
    }


    public TaskService getTaskServiceById(Integer id) {
        return tasks.get(id);
    }
}
