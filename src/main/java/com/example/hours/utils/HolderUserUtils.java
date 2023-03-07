package com.example.hours.utils;

import com.example.hours.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class HolderUserUtils {

    /**
     * 获取当前登录用户id
     * @return 用户id
     */
    public static Integer getLoginUserId() {
        return getLoginUser().getUser().getId();
    }

    /**
     * 获取当前登录用户名称
     * @return 用户名称
     */
    // TODO 改为用户名称
    public static String getLoginUserName() {
        return getLoginUser().getUser().getNickname();
    }

    /**
     * 获取当前登录用户
     * @return 用户
     */
    public static LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (LoginUser) authentication.getPrincipal();
    }
}
