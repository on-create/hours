package com.example.hours.controller;

import com.example.hours.entity.User;
import com.example.hours.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制类
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户 id 获取用户信息
     * @param id 用户 id
     * @return 用户信息
     */
    @GetMapping("/test")
    public User test(@RequestParam Integer id) {
        return userService.getById(id);
    }
}
