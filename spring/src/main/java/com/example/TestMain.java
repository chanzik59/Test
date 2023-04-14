package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author chenzhiqin
 * @date 11/4/2023 14:48
 * @info XX
 */
public class TestMain {

    public static void main(String[] args) {
        System.out.println("----单元测试执行开始");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.example");
        Student bean = context.getBean(Student.class);
        System.out.println("----bean:"+bean.getName());

    }

}
