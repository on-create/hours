package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.dao.UserDao;
import com.example.hours.entity.User;
import com.example.hours.service.UserService;
import com.example.hours.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public UserVo getUserVo(Integer id) {
        // TODO 获取指定字段
        User user = this.getById(id);
        if (user == null) {
            // TODO 抛出异常，交由全局异常处理
            throw new RuntimeException("用户不存在");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    @Override
    public void updateUserInfo(User user) {
        this.update(
                user,
                new UpdateWrapper<User>().eq("id", user.getId())
        );
    }
}
