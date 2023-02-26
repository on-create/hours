package com.example.hours.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.hours.entity.LoginUser;
import com.example.hours.service.LoginService;
import com.example.hours.utils.HolderUserUtils;
import com.example.hours.utils.JwtUtils;
import com.example.hours.utils.RedisKeyUtils;
import com.example.hours.utils.RedisUtils;
import com.example.hours.vo.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Map<String, Object> login(LoginUserVo user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 校验失败
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("用户名或密码错误!");
        }

        // 生成 jwt 返回前端
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtils.createJWT(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("token", jwt);

        // 系统用户相关所有信息放入 redis
        redisUtils.set(RedisKeyUtils.loginUserKey(userId), JSON.toJSONString(loginUser));
        return map;
    }

    @Override
    public void logout() {
        Integer loginUserId = HolderUserUtils.getLoginUserId();
        redisUtils.delete(RedisKeyUtils.loginUserKey(loginUserId.toString()));
    }
}
