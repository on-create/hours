package com.example.hours.common.constant;

/**
 * redis 常量
 */
public class RedisConstant {

    /**
     * 验证码键前缀
     */
    public static final String CODE_PREFIX_KEY = "code_";

    /**
     * 验证码过期时间（s）
     */
    public static final long CODE_EXPIRE_TIME = 15 * 60;

    /**
     * 登录用户前缀
     */
    public static final String LOGIN_USER_PREFIX_KEY = "login:";
}
