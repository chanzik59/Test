package com.example.security.controller;

import com.example.security.inout.ResponseResult;
import com.example.security.inout.User;
import com.example.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenzhiqin
 * @date 2022/8/23 10:28
 * @info XX
 */

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/user/login")
    public ResponseResult userLogin(@RequestBody User user) {
        return loginService.userLogin(user);

    }
}
