package com.example.hours.common.enums;

import lombok.Getter;

/**
 * 返回状态码枚举
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(4004, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    WARN(4001, "操作中止");

    private final int code;

    private final String message;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
