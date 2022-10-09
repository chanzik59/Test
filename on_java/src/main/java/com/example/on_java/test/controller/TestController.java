package com.example.on_java.test.controller;

import com.example.on_java.test.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author chenzhiqin
 * @date 2022/10/9 11:06
 * @info XX
 */
@RestController
@Slf4j
public class TestController {

    @Resource
    private Student student;


    @GetMapping("test/ays")
    public String test() throws ExecutionException, InterruptedException {
        String name = student.getName();
        log.warn("收到异步的结果1{}", StringUtils.hasLength(name));
        Future<String> nameResult = student.getNameResult();
        String result = nameResult.get();
        log.warn("收到异步的结果2{}", StringUtils.hasLength(result));
        return name + result;
    }
}
