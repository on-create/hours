package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.User;
import com.example.hours.vo.UserVo;

public interface UserService extends IService<User> {

    UserVo getUserVo(Integer id);

    void updateUserInfo(User user);
}
