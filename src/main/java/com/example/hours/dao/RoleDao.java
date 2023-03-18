package com.example.hours.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hours.entity.sys.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao extends BaseMapper<SysRole> {

    /*List<String> selectRoleKeysByUserId(@Param("userId") Integer userId);

    Integer selectUserCount(@Param("roleIdList") List<Integer> roleIdList);
*/
    /**
     * 通过角色ID查询角色
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    SysRole selectRoleById(@Param("roleId") Integer roleId);

    /**
     * 根据条件分页查询角色数据
     * @param page 分页信息
     * @param sysRole 角色信息
     * @return 角色数据集合信息
     */
    List<SysRole> selectRoleList(IPage<?> page, @Param("sysRole") SysRole sysRole);

    /**
     * 根据用户ID查询角色
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolePermissionByUserId(@Param("userId") Integer userId);
}
