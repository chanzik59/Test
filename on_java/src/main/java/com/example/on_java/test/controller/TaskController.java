package com.example.on_java.test.controller;

import com.example.on_java.scheduled.MyScheduledConfig;
import com.example.on_java.scheduled.TaskChooser;
import com.example.on_java.scheduled.TaskEntity;
import com.example.on_java.scheduled.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenzhiqin
 * @date 2022/9/29 17:38
 * @info XX
 */
@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {

    @Resource
    private MyScheduledConfig scheduledConfig;

    @Resource
    private TaskChooser taskChooser;

    @PostMapping
    public void addTask(@RequestParam("id") Integer id, @RequestParam("expr") String expression, @RequestParam("message") String message) {
        scheduledConfig.refreshTasks(new TaskEntity(id, message, expression));
    }


    @DeleteMapping("/{id}")
    public void stopTask(@PathVariable("id") Integer id) {
        scheduledConfig.stopTasks(id);
    }


    @GetMapping("/temp/{id}")
    public void addTemplateTask(@PathVariable("id") Integer id) {
        boolean done = scheduledConfig.taskIsDone(id);
        if(!done){
         log.warn("原来业务未结束");
         return;
        }
        TaskService taskServiceById = taskChooser.getTaskServiceById(id);
        taskServiceById.handleJob();

    }


}
