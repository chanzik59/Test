package com.example.security.serviceimpl;

import com.example.security.config.RedisCache;
import com.example.security.inout.LoginUser;
import com.example.security.inout.ResponseResult;
import com.example.security.inout.User;
import com.example.security.service.LoginService;
import com.example.security.tool.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author chenzhiqin
 * @date 2022/8/23 11:17
 * @info XX
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult userLogin(User user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        LoginUser loginUser = Optional.ofNullable(authenticate).map(v -> (LoginUser) v.getPrincipal()).orElseThrow(() -> new RuntimeException("用户名称或者密码错误"));
        String token = Optional.ofNullable(loginUser.getSysUser()).map(v -> v.getId().toString()).map(JwtUtil::createJwt).orElse("");
        redisCache.setCache("login:" + loginUser.getSysUser().getId(), loginUser);
        HashMap<String, String> resData = new HashMap<>(1);
        resData.put("token", token);
        return new ResponseResult<Map>(200, "登录成功", resData);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        Long id = loginUser.getSysUser().getId();
        redisCache.delete("login:"+id);
        return new ResponseResult(200,"退出成功");
    }
}
