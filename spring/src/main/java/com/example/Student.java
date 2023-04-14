package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 11/4/2023 14:49
 * @info XX
 */
@Slf4j
@Component
public class Student {
    @Value("小张")
    private String name;



    @Autowired
    private Teacher teacher;
    public Student() {
        log.info("----student的无参数构造方法被执行");
    }

    public Student(Teacher teacher) {
        this.teacher = teacher;
        log.info("----student的有参数构造方法（teacher）被执行");
    }
    public Student(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
        log.info("----student的有参数构造方法（name，teacher）被执行");
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        log.info("----student中的setName方法被调用");
        this.name = name;
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) {
        log.info("----student中的setTeacher方法被调用");
        this.teacher = teacher;

    }
}