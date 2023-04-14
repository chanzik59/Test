package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 11/4/2023 14:48
 * @info XX
 */
@Slf4j
@Component
public class Teacher  {
    private String name="李老师";

    @Autowired
    private Student student;



    public Teacher(Student student) {
        this.student = student;
        log.info("----teacher的有参数构造方法被执行");
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        log.info("----teacher中的setStudent方法被调用");
        this.student = student;
    }
}