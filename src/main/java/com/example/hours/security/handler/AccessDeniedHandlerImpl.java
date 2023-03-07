package com.example.hours.security.handler;

import com.alibaba.fastjson.JSON;
import com.example.hours.common.Result;
import com.example.hours.common.enums.ResultCodeEnum;
import com.example.hours.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result<Object> failedResult = Result.failed(ResultCodeEnum.FORBIDDEN);
        String s = JSON.toJSONString(failedResult);
        WebUtils.renderString(response, s);
    }
}
