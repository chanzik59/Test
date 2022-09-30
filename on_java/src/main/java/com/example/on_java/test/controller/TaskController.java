package com.example.on_java.test.controller;

import com.example.on_java.scheduled.MyScheduledConfig;
import com.example.on_java.scheduled.TaskEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class TaskController {

    @Resource
    private MyScheduledConfig scheduledConfig;

    @PostMapping
    public void addTask(@RequestParam("id") Integer id, @RequestParam("expr") String expression, @RequestParam("message") String message) {
        scheduledConfig.refreshTasks(new TaskEntity(id, message, expression));
    }


    @DeleteMapping("/{id}")
    public void stopTask(@PathVariable("id") Integer id) {
        scheduledConfig.stopTasks(id);
    }


}
