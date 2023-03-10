package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.hours.dao.RoleDao;
import com.example.hours.dao.UserDao;
import com.example.hours.entity.LoginUser;
import com.example.hours.entity.User;
import com.example.hours.utils.HolderUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getNickname, username);
        User user = userDao.selectOne(queryWrapper);

        // 查询不到数据则抛出错误信息
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名错误");
        }

        // TODO 查询权限
        //List<String> list = roleDao.selectRoleKeysByUserId(user.getId());
        // TODO: 打印测试，后续删除
        //System.out.println(list);
        // 封装成 UserDetails 对象返回
        // TODO 更改为set
        List<String> list = new ArrayList<>(Arrays.asList("system:menu:list", "system:menu:query"));
        return new LoginUser(user, new HashSet<>(list));
    }
}
