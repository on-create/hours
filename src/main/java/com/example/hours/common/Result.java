package com.example.hours.common;

import com.example.hours.common.enums.ResultCodeEnum;
import lombok.Data;

import java.util.Map;

/**
 * 通用返回结果封装类
 * @param <T>
 */
@Data
public class Result<T> {

    /**
     * 状态码
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 数据封装
     */
    private T data;

    protected Result() {}

    protected Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), null);
    }
    /**
     * 请求成功，返回结果
     * @param data 获取的数据
     * @param <T> 数据的类型
     * @return 返回结果的封装
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 请求成功，返回结果
     * @param data 返获取的数据
     * @param message 提示信息
     * @param <T> 数据的类型
     * @return 返回结果的封装
     */
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), message, data);
    }

    /**
     * 请求失败
     * @param errorCodeEnum 错误状态码枚举
     * @return 返回结果的封装
     */
    public static <T> Result<T> failed(ResultCodeEnum errorCodeEnum) {
        return new Result<>(errorCodeEnum.getCode(), errorCodeEnum.getMessage(), null);
    }

    /**
     * 请求失败
     * @param errorCode 错误状态码
     * @param message 错误提示信息
     * @return 返回结果的封装
     */
    public static <T> Result<T> failed(int errorCode, String message) {
        return new Result<>(errorCode, message, null);
    }

    /**
     * 请求失败
     * @param message 错误提示信息
     * @return 返回结果的封装
     */
    public static <T> Result<T> failed(String message) {
        return new Result<>(ResultCodeEnum.FAILED.getCode(), message, null);
    }

    /**
     * 请求失败
     * @return 返回结果的封装
     */
    public static <T> Result<T> failed() {
        return failed(ResultCodeEnum.FAILED);
    }

    /**
     * 操作中止
     */
    public static <T> Result<T> warn() {
        return warn(ResultCodeEnum.WARN.getMessage());
    }

    public static <T> Result<T> warn(String message) {
        return new Result<>(ResultCodeEnum.WARN.getCode(), message, null);
    }

    /**
     * 参数验证失败
     * @param errorMap 参数验证失败信息集合
     * @return 验证失败信息结果
     */
    public static Result<Map<String, String>> validateFailed(Map<String, String> errorMap) {
        return new Result<>(ResultCodeEnum.VALIDATE_FAILED.getCode(), ResultCodeEnum.VALIDATE_FAILED.getMessage(), errorMap);
    }
}
