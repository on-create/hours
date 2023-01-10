package com.example.hours.utils;

import java.util.Random;

/**
 * 验证码工具类
 */
public class CodeUtils {

    /**
     * 生成 6 位随机验证码
     * @return 验证码
     */
    public static String getRandomCode() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}
