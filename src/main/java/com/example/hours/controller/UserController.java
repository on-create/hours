package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.common.validator.group.AddGroup;
import com.example.hours.entity.User;
import com.example.hours.model.vo.UserVo;
import com.example.hours.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
     * 获取当前登录用户信息
     * @return {@link Result<User>}
     */
    @GetMapping("my_info")
    @PreAuthorize("permission.hasRole('admin')")
    public Result<User> getMyInfo() {
        User user = userService.getMyInfo();
        return Result.success(user);
    }


    /**
     * 获取当前用户信息
     * @return {@link Result<UserVo>}
     */
    /*@GetMapping("/info")
    public Result<UserVo> getUserVo() {
        UserVo userVo = userService.getUserVo();
        return Result.success(userVo);
    }*/

    /**
     * 更新当前用户信息
     * @param sysUser 当前用户更新的信息
     * @return {@link Result<>}
     */
    /*@PostMapping("/update")
    public Result<Object> updateUser(@RequestBody SysUser sysUser) {
        userService.updateUserInfo(sysUser);
        return Result.success();
    }*/

    /**
     * 用户注册
     * @param sysUser 注册用户信息
     * @return {@link Result<>}
     */
    /*@PostMapping("/register")
    public Result<Object> register(@Validated(AddGroup.class) @RequestBody SysUser sysUser) {
        userService.addUser(sysUser);
        return Result.success();
    }*/

    /**
     * 发送邮箱验证码
     * @param email 邮箱地址
     * @return {@link Result<>}
     */
    /*@GetMapping("/code")
    public Result<Object>sendCode(
            @NotBlank(message = "邮箱不能为空")
            @Email(message = "邮箱格式错误")
            @RequestParam("email") String email) {
        userService.sendCode(email);
        return Result.success();
    }*/
}
