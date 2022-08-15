package com.example.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenzhiqin
 * @date 2022/8/12 14:29
 * @info XX
 */
@RestController
public class WebController {

    @RequestMapping("/hello")
    public String hello(){
        return  "hello";
    }
}
