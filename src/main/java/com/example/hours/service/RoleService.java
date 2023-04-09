package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.Role;
import com.example.hours.entity.UserRole;
import com.example.hours.model.pagination.RolePage;
import com.example.hours.model.vo.AuthUserVO;
import com.example.hours.model.vo.StatusVO;
import com.example.hours.utils.page.PageResult;

import java.util.List;

public interface RoleService extends IService<Role> {

    /**
     * 获取所有角色信息
     * @return 角色列表
     */
    List<Role> getAllRoles();

    /**
     * 分页获取角色信息
     * @param rolePage 分页请求信息
     * @return 角色列表信息
     */
    PageResult getRoleInfos(RolePage rolePage);

    /**
     * 添加角色
     * @param role 角色信息
     */
    void insertRole(Role role);

    /**
     * 根据角色ID获取角色信息
     * @param roleId 角色ID
     * @return 角色信息
     */
    Role getRoleInfoById(Integer roleId);

    /**
     * 删除角色信息
     * @param roleIds 角色id列表
     */
    void deleteRoles(List<Integer> roleIds);

    /**
     * 根据角色ID修改角色状态
     * @param statusVO 角色状态信息
     */
    void changeStatus(StatusVO statusVO);

    /**
     * 取消授权用户角色
     * @param userRole 用户和角色关联信息
     */
    void cancelAuthUser(UserRole userRole);

    /**
     * 批量取消授权用户角色
     * @param authUserVO 授权用户信息
     */
    void cancelAuthUserAll(AuthUserVO authUserVO);

    /**
     * 批量选择用户授权
     * @param authUserVO 授权用户信息
     */
    void insertAuthUsers(AuthUserVO authUserVO);
}
