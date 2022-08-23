package com.example.security.service;

import com.example.security.inout.ResponseResult;
import com.example.security.inout.User;
import com.example.security.inout.dao.SysUser;
import org.springframework.stereotype.Service;

/**
 * @author chenzhiqin
 * @date 2022/8/23 10:14
 * @info XX
 */
public interface LoginService {


    /**
     * 用户登录
     * @param user
     * @return
     */
    ResponseResult userLogin(User user);
}
