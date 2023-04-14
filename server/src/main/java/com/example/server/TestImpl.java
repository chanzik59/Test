package com.example.server;

import com.alibaba.dubbo.config.annotation.Service;
import com.com.example.inf.TestInf;
import com.com.example.inf.User;
import lombok.extern.slf4j.Slf4j;


/**
 * @author chenzhiqin
 * @date 29/3/2023 15:55
 * @info XX
 */
@Slf4j
@Service(timeout = 3000)
public class TestImpl implements TestInf {
    @Override
    public String test() {

        log.info("被调用 server");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "666";
    }

    @Override
    public User findUser() {
        log.info("寻找小张");
        return new User("小张", 16);
    }

    @Override
    public void testFef() {

    }
}
