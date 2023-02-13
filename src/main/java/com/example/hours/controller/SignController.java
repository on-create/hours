package com.example.hours.controller;

import com.example.hours.common.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 签到、签退 控制类
 */
@RestController
@RequestMapping("/sign")
public class SignController {

    @PostMapping("/")
    public Result<Object> signIn() {

        return Result.success();
    }

    public Result<Object> signOut() {

        return Result.success();
    }
}
