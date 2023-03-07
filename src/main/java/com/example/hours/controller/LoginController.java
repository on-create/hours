package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.service.LoginService;
import com.example.hours.model.vo.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 登录控制类
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     * @param loginUserVo 用户vo信息
     * @return {@link Result<Map>}
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Validated @RequestBody LoginUserVo loginUserVo) {
        Map<String, Object> map = loginService.login(loginUserVo);
        return Result.success(map, "登录成功");
    }

    /**
     * 退出
     * @return {@link Result<>}
     */
    @PostMapping("/my_logout")
    public Result<Object> logout() {
        loginService.logout();
        return Result.success("退出成功");
    }
}
