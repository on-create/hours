package com.example.hours.utils;

import com.example.hours.common.constant.RedisConstant;

/**
 * redis key 工具类
 */
public class RedisKeyUtils {

    /**
     * 生成验证码键
     * @param key key
     * @return 验证码键
     */
    public static String codeKey(String key) {
        return RedisConstant.CODE_PREFIX_KEY + key;
    }

    /**
     * 生成登录用户键
     * @param key userid
     * @return 登录用户键
     */
    public static String loginUserKey(String key) {
        return RedisConstant.LOGIN_USER_PREFIX_KEY + key;
    }
}
