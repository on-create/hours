package com.example.hours.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hours.entity.Role;
import com.example.hours.model.RoleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao extends BaseMapper<Role> {

    List<String> selectRoleKeysByUserId(@Param("userId") Integer userId);

    Integer selectUserCount(@Param("roleIdList") List<Integer> roleIdList);

    /**
     * 通过角色ID查询角色
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    Role selectRoleById(@Param("roleId") Integer roleId);

    List<Role> selectRoleList(IPage<?> page, @Param("role") Role role);
}
