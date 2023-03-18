package com.example.hours.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hours.entity.sys.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMenuDao extends BaseMapper<RoleMenu> {

    /**
     * 批量新增角色菜单信息
     * @param roleMenuList 角色菜单列表
     */
    void addBatchRoleMenu(@Param("list") List<RoleMenu> roleMenuList);

    /**
     * 通过角色ID删除角色和菜单关联
     * @param roleId 角色ID
     */
    void deleteRoleMenuByRoleId(@Param("roleId") Integer roleId);

    /**
     * 批量删除角色菜单关联信息
     * @param roleIds 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchRoleMenu(@Param("roleIds") List<Integer> roleIds);

    /**
     * 查询菜单使用数量
     * @param menuId 菜单ID
     * @return 结果
     */
    int checkMenuExistRole(@Param("menuId") Integer menuId);
}
