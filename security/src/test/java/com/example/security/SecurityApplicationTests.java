package com.example.security;

import com.example.security.inout.dao.SysUser;
import com.example.security.inout.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class securityapplicationtests {

    @Autowired
    private SysUserMapper userMapper;

    @Test
    void contextLoads() {
        SysUser sysUser = userMapper.selectByPrimaryKey(1L);
        System.out.println(sysUser);
    }


}
