package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.entity.RegisterActivity;
import com.example.hours.service.RegisterActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 活动报名 控制类
 */
@RestController
@RequestMapping("/register")
public class RegisterActivityController {

    @Autowired
    private RegisterActivityService registerActivityService;

    /**
     * 活动报名
     * @param registerActivity 注册信息
     * @return {@link Result<>}
     */
    @PostMapping("/sign_up")
    public Result<Object> registerActivity(@Validated @RequestBody RegisterActivity registerActivity) {
        registerActivityService.registerActivity(registerActivity);
        return Result.success();
    }

    /**
     * 用户是否已报名
     * @param activityId 活动id
     * @return 是否已报名
     */
    @GetMapping("/isSign")
    public Result<Boolean> isSign(@RequestParam("activityId") Integer activityId) {
        Boolean res = registerActivityService.isSign(activityId);
        return Result.success(res);
    }
}
