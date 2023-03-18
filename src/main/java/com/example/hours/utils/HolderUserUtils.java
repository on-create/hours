package com.example.hours.utils;

import com.example.hours.entity.sys.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HolderUserUtils {

    /**
     * 获取当前登录用户id
     * @return 用户id
     */
    public static Integer getLoginUserId() {
        return getLoginUser().getSysUser().getId();
    }

    /**
     * 获取当前登录用户名称
     * @return 用户名称
     */
    public static String getLoginUserName() {
        return getLoginUser().getSysUser().getUsername();
    }

    /**
     * 获取当前登录用户
     * @return 用户
     */
    public static LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LoginUser) authentication.getPrincipal();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否位管理员
     * @param userId 用户id
     * @return 结果
     */
    public static boolean isAdmin(Integer userId) {
        return userId != null && userId == 1;
    }
}
