package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.model.vo.LoginUserVO;
import com.example.hours.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginUserVO loginUserVo) {
        Map<String, Object> map = loginService.login(loginUserVo);
        return Result.success(map, "登录成功");
    }
}
