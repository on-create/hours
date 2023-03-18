package com.example.hours.service;

import com.example.hours.entity.sys.SysUser;

import java.util.Set;

public interface PermissionService {

    /**
     * 获取角色数据权限
     * @param sysUser 用户信息
     * @return 角色权限信息
     */
    Set<String> getRolePermission(SysUser sysUser);

    /**
     * 获取菜单数据权限
     * @param sysUser 用户信息
     * @return 菜单权限信息
     */
    Set<String> getMenuPermission(SysUser sysUser);
}
