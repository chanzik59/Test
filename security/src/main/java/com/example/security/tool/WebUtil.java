package com.example.security.tool;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author chenzhiqin
 * @date 2022/8/22 14:54
 * @info XX
 */
@Slf4j
public class WebUtil {

    public static void renderString(HttpServletResponse response, String message) {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        try {
            response.getWriter().write(message);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
