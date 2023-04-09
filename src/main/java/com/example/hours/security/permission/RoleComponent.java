package com.example.hours.security.permission;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.hours.entity.LoginUser;
import com.example.hours.utils.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Set;

@Component("permission")
public class RoleComponent {

    /**
     * 管理员，拥有所有权限
     */
    public static final String ADMIN = "admin";

    /**
     * 验证当前登录用户是否具备该角色
     * @param role 角色名称
     * @return boolean
     */
    public final boolean hasRole(String role) {
        return hasAnyRole(role);
    }

    /**
     * 验证当前登录用户是否是某一角色
     * @param roles 角色列表
     * @return boolean
     */
    public final boolean hasAnyRole(String... roles) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (Objects.isNull(loginUser) || StringUtils.isBlank(loginUser.getRole())) {
            return false;
        }

        // 获取当前登录用户的角色key
        String roleKey = loginUser.getRole();
        for (String role : roles) {
            if (ADMIN.equals(roleKey) || roleKey.equals(role)) {
                return true;
            }
        }
        return false;
    }
}
