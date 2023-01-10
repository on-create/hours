package com.example.hours.exception.handler;

import com.example.hours.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.example.hours.controller")
public class GlobalExceptionHandler {

    /**
     * 参数异常处理
     * @param e MethodArgumentNotValidException
     * @return 参数异常字段及校验失败原因的 map 集合封装的返回结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Map<String, String>> handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验异常{}，异常类型{}", e.getMessage(), e.getClass());

        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        // 获取校验的错误结果
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return Result.validateFailed(errorMap);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result<Map<String, String>> handleValidException(ConstraintViolationException e) {
        log.error("数据校验异常{}，异常类型{}", e.getMessage(), e.getClass());

        Map<String, String> errorMap = new HashMap<>();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        violations.forEach(constraintViolation -> {
            String[] strings = constraintViolation.getPropertyPath().toString().split("\\.");
            String key = strings[strings.length - 1];
            String value = constraintViolation.getMessage();
            errorMap.put(key, value);
        });
        return Result.validateFailed(errorMap);
    }
}
