package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.entity.User;
import com.example.hours.service.UserService;
import com.example.hours.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user_info")
    public Result<UserVo> getUserVo(@RequestParam("id") Integer id) {
        UserVo userVo = userService.getUserVo(id);
        return Result.success(userVo);
    }

    @PostMapping("/update")
    public Result<Object> updateUser(@RequestBody User user) {
        userService.updateUserInfo(user);
        return Result.success();
    }
}
