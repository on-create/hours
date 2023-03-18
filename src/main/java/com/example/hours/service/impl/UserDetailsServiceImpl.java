package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.hours.entity.LoginUser;
import com.example.hours.entity.User;
import com.example.hours.mapper.RoleMapper;
import com.example.hours.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
        );

        // 查询不到数据则抛出错误信息
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名错误");
        }

        // 封装成 UserDetails 对象返回
        Set<String> roleKeys = roleMapper.getRoleKeyByUserId(user.getId());
        return new LoginUser(user, roleKeys);
    }
}
