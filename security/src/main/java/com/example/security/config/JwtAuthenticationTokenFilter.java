package com.example.security.config;

import com.example.security.inout.LoginUser;
import com.example.security.tool.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author chenzhiqin
 * @date 2022/8/23 11:43
 * @info XX
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        Claims claims = JwtUtil.parseJwt(token);
        String userId = claims.getSubject();
        String redisKey = "login:" + userId;
        LoginUser loginUser = Optional.ofNullable(redisCache.getCacheValue(redisKey)).map(v -> (LoginUser) v).orElseThrow(() -> new RuntimeException("用户未登录"));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}
