package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.User;

public interface UserService extends IService<User> {

    /**
     * 获取当前登录用户信息
     * @return 用户信息
     */
    User getMyInfo();

    /**
     * 根据用户id获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    User getUserInfoById(Integer userId);
}
