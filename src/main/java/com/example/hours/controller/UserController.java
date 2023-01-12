package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.common.validator.group.AddGroup;
import com.example.hours.entity.User;
import com.example.hours.service.UserService;
import com.example.hours.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 用户控制类
 */
@RestController
@RequestMapping("/user")
@Validated
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

    @GetMapping("/info")
    public Result<UserVo> getUserVo(@RequestParam("id") Integer id) {
        UserVo userVo = userService.getUserVo(id);
        return Result.success(userVo);
    }

    /**
     * 更新当前用户信息
     * @param user 当前用户更新的信息
     * @return {@link Result<>}
     */
    @PostMapping("/update")
    public Result<Object> updateUser(@RequestBody User user) {
        userService.updateUserInfo(user);
        return Result.success();
    }

    /**
     * 用户注册
     * @param user 注册用户信息
     * @return {@link Result<>}
     */
    @PostMapping("/register")
    public Result<Object> register(@Validated(AddGroup.class) @RequestBody User user) {
        userService.addUser(user);
        return Result.success();
    }

    /**
     * 发送邮箱验证码
     * @param email 邮箱地址
     * @return {@link Result<>}
     */
    @GetMapping("/code")
    public Result<Object>sendCode(
            @NotBlank(message = "邮箱不能为空")
            @Email(message = "邮箱格式错误")
            @RequestParam("email") String email) {
        userService.sendCode(email);
        return Result.success();
    }
}
