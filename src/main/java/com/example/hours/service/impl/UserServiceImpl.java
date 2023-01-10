package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.dao.UserDao;
import com.example.hours.entity.User;
import com.example.hours.service.UserService;
import com.example.hours.utils.CodeUtils;
import com.example.hours.vo.EmailVo;
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

    @Override
    public void addUser(User user) {
        String email = user.getEmail();
        if (this.getBaseMapper().getUserIdByEmail(email) != null) {
            throw new RuntimeException("该邮箱已使用");
        }
        this.save(user);
    }

    @Override
    public void sendCode(String email) {
        // 生成 6 位随机验证码
        String code = CodeUtils.getRandomCode();
        // 发送验证码
        EmailVo emailVo = EmailVo.builder()
                .email(email)
                .subject("验证码")
                .content("您的验证码为 " + code + " ，有效期15分钟，请不要告诉他人!")
                .build();
        // TODO 通过 rabbitmq 发送邮件
        // TODO 将验证码存入 redis，并设置过期时间
    }
}
