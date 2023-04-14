package com.example.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.com.example.inf.TestInf;
import com.com.example.inf.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenzhiqin
 * @date 29/3/2023 13:50
 * @info XX
 */

@Controller(value = "dupNewsController")
@Slf4j
public class DubboController {


    //    @Autowired
//    @Qualifier(value = "test")
//    @Reference(url = "dubbo://localhost:20882", timeout = 20000)
    @Reference(timeout = 10000)
    private TestInf testInf;

    @RequestMapping("test")
    @ResponseBody
    public String test() {
        log.info("被执行 consumer");
        return testInf.test();
    }


    @RequestMapping("user")
    @ResponseBody
    public String User() {
        log.info("被执行 consumer");

        User user = testInf.findUser();
        return user.toString();
    }


    @RequestMapping("event")
    @ResponseBody
    public String event() {
        log.info("被执行 event");
        MyEvent event = new MyEvent("小张");
        MyPublish.publish(event);
        return testInf.test();
    }



}
