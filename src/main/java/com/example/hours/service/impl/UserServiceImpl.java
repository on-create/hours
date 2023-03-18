package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.entity.User;
import com.example.hours.mapper.UserMapper;
import com.example.hours.service.UserService;
import com.example.hours.utils.HolderUserUtils;
import com.example.hours.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getMyInfo() {
        Integer loginUserId = SecurityUtils.getUserId();
        if (Objects.nonNull(loginUserId)) {
            return getUserInfoById(loginUserId);
        }
        return null;
    }

    @Override
    public User getUserInfoById(Integer userId) {
        return this.baseMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getId, userId)
                        .select(
                                User::getUsername, User::getEmail,
                                User::getPhone, User::getSex
                        )
        );
    }
}
