package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.User;
import com.example.hours.model.vo.UserVo;

public interface UserService extends IService<User> {

    UserVo getUserVo();

    void updateUserInfo(User user);

    void addUser(User user);

    void sendCode(String email);
}
