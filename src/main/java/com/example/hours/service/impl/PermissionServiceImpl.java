package com.example.hours.service.impl;

import com.example.hours.entity.sys.SysRole;
import com.example.hours.entity.sys.SysUser;
import com.example.hours.service.MenuService;
import com.example.hours.service.PermissionService;
import com.example.hours.service.sys.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    // TODO 和permissionService循环依赖，后期优化解耦
    @Autowired
    @Lazy
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 获取角色数据权限
     * @param sysUser 用户信息
     * @return 角色权限信息
     */
    @Override
    public Set<String> getRolePermission(SysUser sysUser) {
        Set<String> roles = new HashSet<>();
        // 管理员拥有所有权限
        if (sysUser.isAdmin()) {
            roles.add("admin");
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(sysUser.getId()));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     * @param sysUser 用户信息
     * @return 菜单权限信息
     */
    @Override
    public Set<String> getMenuPermission(SysUser sysUser) {
        Set<String> perms = new HashSet<>();
        // 管理员拥有所有权限
        if (sysUser.isAdmin()) {
            perms.add("*:*:*");
        } else {
            List<SysRole> sysRoles = sysUser.getSysRoles();
            if (!sysRoles.isEmpty() && sysRoles.size() > 1) {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (SysRole sysRole : sysRoles) {
                    Set<String> rolePerms = menuService.selectMenuPermsByRoleId(sysRole.getId());
                    sysRole.setPermissions(rolePerms);
                    perms.addAll(rolePerms);
                }
            } else {
                perms.addAll(menuService.selectMenuPermsByUserId(sysUser.getId()));
            }
        }
        return perms;
    }
}
