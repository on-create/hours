package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.common.validator.group.AddGroup;
import com.example.hours.entity.User;
import com.example.hours.model.bo.UserInfo;
import com.example.hours.model.pagination.UserPage;
import com.example.hours.model.vo.UserVo;
import com.example.hours.service.UserService;
import com.example.hours.utils.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
    public Result<UserInfo> getMyInfo() {
        UserInfo userInfo = userService.getMyInfo();
        return Result.success(userInfo);
    }

    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/info")
    public Result<UserInfo> getUserInfo(@RequestParam("userId") Integer userId) {
        UserInfo userInfo = userService.getUserInfoById(userId);
        return Result.success(userInfo);
    }

    /**
     * 分页，批量获取用户信息
     * @param userPage 请求信息
     * @return 用户信息
     */
    @GetMapping("/list")
    @PreAuthorize("@permission.hasRole('admin')")
    public Result<PageResult> getUserList(UserPage userPage) {
        PageResult result = userService.getUserInfos(userPage);
        return Result.success(result);
    }

    /**
     * 添加用户
     * @param user 用户信息
     * @return {@link Result}
     */
    @PostMapping("/add")
    @PreAuthorize("@permission.hasRole('admin')")
    public Result<?> addUser(@RequestBody User user) {
        userService.insertUser(user);
        return Result.success();
    }

    /**
     * 更新用户信息
     * @param user 最新信息
     * @return {@link Result}
     */
    @PostMapping("/update")
    public Result<?> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success();
    }

    /**
     * 删除用户信息
     * @param userIds 用户id列表
     * @return {@link Result}
     */
    @PostMapping("/delete")
    @PreAuthorize("@permission.hasRole('admin')")
    public Result<?> deleteUser(@RequestBody List<Integer> userIds) {
        userService.deleteUser(userIds);
        return Result.success();
    }

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
