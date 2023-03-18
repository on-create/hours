package com.example.hours.security.permission;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.hours.entity.sys.LoginUser;
import com.example.hours.utils.HolderUserUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Set;

@Component("perms")
public class PermissionComponent {

    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";

    /**
     * 验证用户是否具备某权限
     * @param permission 权限字符串
     * @return 用户是否具备 permission 权限
     */
    public boolean hasPermission(String permission) {
        if (StringUtils.isBlank(permission)) {
            return false;
        }

        return hasAnyPermission(permission);
    }

    /**
     * 验证用户是否具备某一权限
     * @param permissions 权限字符串列表
     * @return 用户是否具备 permissions 中的任意一个权限
     */
    public boolean hasAnyPermission(String... permissions) {
        LoginUser loginUser = HolderUserUtils.getLoginUser();
        if (Objects.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }

        // 当前用户权限列表
        Set<String> userPermissions = loginUser.getPermissions();
        for (String permission : permissions) {
            if (userPermissions.contains(ALL_PERMISSION) || userPermissions.contains(permission)) {
                return true;
            }
        }
        return false;
    }
}
