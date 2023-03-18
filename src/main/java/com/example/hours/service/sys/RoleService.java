package com.example.hours.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.sys.SysRole;
import com.example.hours.entity.sys.UserRole;
import com.example.hours.utils.page.PageParams;
import com.example.hours.utils.page.PageResult;

import java.util.List;
import java.util.Set;

public interface RoleService extends IService<SysRole> {

    /*List<RoleVO> getRoleVOs(Integer status);

    void saveOrUpdateRole(RoleVO roleVO);*/

    //void deleteRoles(List<Integer> roleIdList);

    /**
     * 根据条件分页查询角色数据
     * @param sysRole 角色信息
     * @param params 分页信息
     * @return 角色数据分页信息
     */
    PageResult selectRoleList(SysRole sysRole, PageParams params);

    /**
     * 校验角色是否有数据权限
     * @param roleId 角色id
     */
    void checkRoleDataScope(Integer roleId);

    /**
     * 通过角色ID查询角色
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    SysRole selectRoleById(Integer roleId);

    /**
     * 新增保存角色信息
     * @param sysRole 角色信息
     */
    void insertRole(SysRole sysRole);

    /**
     * 修改角色信息
     * @param sysRole 角色信息
     */
    void updateRole(SysRole sysRole);

    /**
     * 根据用户ID查询权限
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectRolePermissionByUserId(Integer userId);

    /**
     * 修改数据权限信息
     * @param sysRole 角色信息
     */
    void updateAuthDataScope(SysRole sysRole);

    /**
     * 修改角色状态
     * @param sysRole 角色信息
     */
    void updateRoleStatus(SysRole sysRole);

    /**
     * 批量删除角色信息
     * @param roleIds 需要删除的角色ID
     */
    void deleteRoleByIds(List<Integer> roleIds);

    /**
     * 获取所有角色信息
     * @return 角色列表
     */
    List<SysRole> selectRolesAll();

    /**
     * 根据用户ID查询角色
     * @param userId 用户id
     * @return 角色列表
     */
    List<SysRole> selectRoleByUserId(Integer userId);

    /**
     * 取消授权用户角色
     * @param userRole 用户和角色关联信息
     */
    void deleteAuthUser(UserRole userRole);

    /**
     * 批量取消授权用户角色
     * @param roleId 角色ID
     * @param userIds 需要取消授权的用户数据ID
     */
    void deleteAuthUsers(Integer roleId, List<Integer> userIds);

    /**
     * 批量选择授权用户角色
     * @param roleId 角色ID
     * @param userIds 需要授权的用户数据ID
     */
    void insertAuthUsers(Integer roleId, List<Integer> userIds);
}
