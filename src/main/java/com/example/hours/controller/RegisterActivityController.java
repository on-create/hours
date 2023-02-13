package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.service.RegisterActivityService;
import com.example.hours.vo.RegisterActivityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 活动报名 控制类
 */
@RestController
@RequestMapping("/register_activity")
public class RegisterActivityController {

    @Autowired
    private RegisterActivityService registerActivityService;

    /**
     * 活动报名
     * @param registerActivityVo 注册信息
     * @return {@link Result<>}
     */
    @PostMapping("/sign_up")
    public Result<Object> registerActivity(@Validated @RequestBody RegisterActivityVo registerActivityVo) {
        registerActivityService.registerActivity(registerActivityVo);
        return Result.success();
    }
}
