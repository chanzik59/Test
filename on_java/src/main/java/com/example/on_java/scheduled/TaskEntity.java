package com.example.on_java.scheduled;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenzhiqin
 * @date 2022/9/29 16:36
 * @info XX
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    /**
     * 任务id
     */
    private Integer taskId;

    /**
     * 任务描述
     */
    private String desc;

    /**
     * 表达式
     */
    private String expression;
}
