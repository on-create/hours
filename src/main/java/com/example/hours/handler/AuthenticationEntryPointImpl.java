package com.example.hours.handler;

import com.alibaba.fastjson.JSON;
import com.example.hours.common.Result;
import com.example.hours.common.enums.ResultCodeEnum;
import com.example.hours.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户未登录
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result<Object> failedResult = Result.failed(ResultCodeEnum.UNAUTHORIZED);
        String s = JSON.toJSONString(failedResult);
        WebUtils.renderString(response, s);
    }
}
