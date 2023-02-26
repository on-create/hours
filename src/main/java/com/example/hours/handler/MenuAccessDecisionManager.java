package com.example.hours.handler;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 获取用户权限列表
        List<String> permissionList = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        for (ConfigAttribute configAttribute : configAttributes) {
            // 匿名访问直接放行
            if ("anonymous".equals(configAttribute.getAttribute())) {
                return;
            }

            if ("db error".equals(configAttribute.getAttribute())) {
                throw new AccessDeniedException("db 数据错误");
            }

            if ("error".equals(configAttribute.getAttribute())) {
                throw new AccessDeniedException("请求地址已禁用");
            }

            if (permissionList.contains(configAttribute.getAttribute())) {
                return;
            }
        }

        throw new AccessDeniedException("没有操作权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
