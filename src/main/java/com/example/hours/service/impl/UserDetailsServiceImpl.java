package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.hours.common.constant.EntityConstant;
import com.example.hours.entity.LoginUser;
import com.example.hours.entity.Role;
import com.example.hours.entity.User;
import com.example.hours.mapper.RoleMapper;
import com.example.hours.mapper.UserMapper;
import com.example.hours.mapper.UserRoleMapper;
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

    @Autowired
    private UserRoleMapper userRoleMapper;

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
        int roleId = userRoleMapper.selectRoleId(user.getId());
        Role role = roleMapper.selectOne(
                new LambdaQueryWrapper<Role>()
                        .eq(Role::getId, roleId)
                        .select(Role::getKey, Role::getStatus)
        );
        // 判断role的状态，如果关联角色已停用，则显示为普通用户
        if (EntityConstant.COMMON_DISABLE.equals(role.getStatus())) {
            role = roleMapper.selectOne(
                    new LambdaQueryWrapper<Role>()
                            .eq(Role::getId, EntityConstant.ROLE_COMMON_ID)
                            .select(Role::getKey)
            );
        }
        return new LoginUser(user, role.getKey());
    }
}
