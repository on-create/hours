package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    /**
     * 获取所有角色信息
     * @return 角色列表
     */
    List<Role> getAllRoles();
}
