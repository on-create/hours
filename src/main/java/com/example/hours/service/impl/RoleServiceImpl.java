package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.common.constant.CommonConstant;
import com.example.hours.common.constant.EntityConstant;
import com.example.hours.entity.Role;
import com.example.hours.entity.UserRole;
import com.example.hours.exception.HourException;
import com.example.hours.mapper.RoleMapper;
import com.example.hours.mapper.UserRoleMapper;
import com.example.hours.model.pagination.RolePage;
import com.example.hours.model.vo.AuthUserVO;
import com.example.hours.model.vo.StatusVO;
import com.example.hours.service.RoleService;
import com.example.hours.utils.page.PageResult;
import com.example.hours.utils.page.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

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

    /**
     * 分页获取角色信息
     * @param rolePage 分页请求信息
     * @return 角色信息列表
     */
    @Override
    public PageResult getRoleInfos(RolePage rolePage) {
        // TODO 添加过滤条件
        IPage<Role> roleIPage = this.baseMapper.selectPage(
                PageUtils.initPage(rolePage),
                new LambdaQueryWrapper<Role>()
                        .eq(Objects.nonNull(rolePage.getRoleName()), Role::getName, rolePage.getRoleName())
                        .eq(Objects.nonNull(rolePage.getRoleKey()), Role::getKey, rolePage.getRoleKey())
                        .eq(Objects.nonNull(rolePage.getStatus()), Role::getStatus, rolePage.getStatus())
                        .orderByAsc(Role::getSort)
        );
        return new PageResult(roleIPage);
    }

    /**
     * 添加角色
     * @param role 角色信息
     */
    @Override
    public void insertRole(Role role) {
        if (!checkRoleNameUnique(role.getName())) {
            throw new HourException("新增角色'" + role.getName() + "'失败，角色名称已存在");
        }

        if (!checkRoleKeyUnique(role.getKey())) {
            throw new HourException("新增角色'" + role.getName() + "'失败，角色权限已存在");
        }
        // 默认启用状态
        role.setStatus(EntityConstant.COMMON_ENABLE);
        // 未删除
        role.setDelFlag(EntityConstant.COMMON_UNDELETED);
        this.save(role);
    }

    /**
     * 根据角色ID获取角色信息
     * @param roleId 角色ID
     * @return 角色信息
     */
    @Override
    public Role getRoleInfoById(Integer roleId) {
        return this.baseMapper.selectOne(
                new LambdaQueryWrapper<Role>()
                        .eq(Role::getId, roleId)
                        .select(Role::getId, Role::getName, Role::getKey, Role::getSort, Role::getStatus)
        );
    }

    /**
     * 删除角色信息
     * @param roleIds 角色id列表
     */
    @Override
    public void deleteRoles(List<Integer> roleIds) {
        for (Integer roleId : roleIds) {
            if (userRoleMapper.selectCountByRoleId(roleId) > 0) {
                Role role = this.baseMapper.selectOne(
                        new LambdaQueryWrapper<Role>()
                                .eq(Role::getId, roleId)
                                .select(Role::getName)
                );
                throw new HourException(String.format("%1$s已分配,不能删除", role.getName()));
            }
        }
        this.baseMapper.deleteBatchIds(roleIds);
    }

    /**
     * 根据角色ID修改角色状态
     * @param statusVO 角色状态信息
     */
    @Override
    public void changeStatus(StatusVO statusVO) {
        Integer roleId = statusVO.getId();
        // 禁止修改`管理员`和`普通用户`的启用状态
        if (EntityConstant.ROLE_ADMIN_ID.equals(roleId) || EntityConstant.ROLE_COMMON_ID.equals(roleId)) {
            throw new HourException("禁止修改该角色状态");
        }
        Role role = Role.builder()
                .id(statusVO.getId())
                .status(statusVO.getStatus())
                .build();
        this.baseMapper.updateById(role);
    }

    /**
     * 取消授权用户角色
     * @param userRole 用户和角色关联信息
     */
    @Override
    public void cancelAuthUser(UserRole userRole) {
        // 判断是否是普通用户
        if (EntityConstant.ROLE_COMMON_ID.equals(userRole.getRoleId())) {
            throw new HourException("禁止取消普通用户授权");
        }
        // 更新为普通用户
        userRoleMapper.updateRoleId(userRole.getUserId(), EntityConstant.ROLE_COMMON_ID);
    }

    /**
     * 批量取消授权用户角色
     * @param authUserVO 授权用户信息
     */
    @Override
    public void cancelAuthUserAll(AuthUserVO authUserVO) {
        // 判断是否是普通用户
        if (EntityConstant.ROLE_COMMON_ID.equals(authUserVO.getRoleId())) {
            throw new HourException("禁止取消普通用户授权");
        }
        // 批量更新为普通用户
        authUserVO.setRoleId(EntityConstant.ROLE_COMMON_ID);
        userRoleMapper.updateRoleIdByUserIds(authUserVO);
    }

    /**
     * 批量选择用户授权
     * @param authUserVO 授权用户信息
     */
    @Override
    public void insertAuthUsers(AuthUserVO authUserVO) {
        userRoleMapper.updateRoleIdByUserIds(authUserVO);
    }

    /**
     * 检查角色名是否唯一
     * @param roleName 角色名称
     * @return 结果
     */
    private boolean checkRoleNameUnique(String roleName) {
        Role role = this.baseMapper.selectOne(
                new LambdaQueryWrapper<Role>()
                        .eq(Role::getName, roleName)
                        .select(Role::getId)
        );

        if (Objects.nonNull(role)) {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }

    /**
     * 检查角色权限字符串是否唯一
     * @param roleKey 角色权限字符串
     * @return 结果
     */
    private boolean checkRoleKeyUnique(String roleKey) {
        Role role = this.baseMapper.selectOne(
                new LambdaQueryWrapper<Role>()
                        .eq(Role::getKey, roleKey)
                        .select(Role::getId)
        );

        if (Objects.nonNull(role)) {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }
}
