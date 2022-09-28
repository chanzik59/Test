package com.example.on_java.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.util.Calendar;

/**
 * @author chenzhiqin
 * @date 2022/8/26 14:57
 * @info XX
 */
@RestController
@Slf4j
public class GatewayController {

    @RequestMapping("/gateway/health")
    public void healCheck(HttpServletRequest request, HttpServletResponse response) {
        boolean stopping = true;
        if (stopping) {
            long num = Calendar.getInstance().get(Calendar.SECOND);
            num = num % 10;
            if (num == 0) {
                log.info("接收到客户端的请求 : " + request.getRequestURI() + " stop flag : " + stopping);
            }
        }
        response.setStatus(stopping ? HttpURLConnection.HTTP_NOT_ACCEPTABLE : HttpURLConnection.HTTP_OK);
    }


    @RequestMapping("/gateway/stopping/{time}")
    public void stopping(@PathVariable String time, HttpServletResponse response) {
        boolean STOPPING = false;
        if (STOPPING) {
            log.info(String.format("网关系统正在关闭，关闭时间%s,Stopping:%b", time, true));
        } else {
            response.setStatus(400);
            log.warn("状态设置异常" + STOPPING);
        }
    }


    @RequestMapping("/gateway/8583")
    public String iso8583(HttpServletRequest request, HttpServletResponse response) {
       log.info(request.toString());

        return "test";

    }
}
