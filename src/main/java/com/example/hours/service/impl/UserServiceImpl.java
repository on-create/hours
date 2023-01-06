package com.example.hours.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.dao.UserDao;
import com.example.hours.entity.User;
import com.example.hours.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
