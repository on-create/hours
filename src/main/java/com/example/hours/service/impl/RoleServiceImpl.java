package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.entity.Role;
import com.example.hours.mapper.RoleMapper;
import com.example.hours.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    /**
     * 获取所有角色信息
     * @return 角色列表
     */
    @Override
    public List<Role> getAllRoles() {
        return this.baseMapper.selectList(
                new LambdaQueryWrapper<Role>()
                        .select(Role::getId, Role::getName, Role::getStatus)
        );
    }
}
