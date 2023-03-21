package com.example.hours.mapper;

import com.example.hours.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    /**
     * 根据用户ID获取角色ID
     * @param userId 用户ID
     * @return 角色ID
     */
    Integer selectRoleId(@Param("userId") Integer userId);

    /**
     * 批量添加数据
     * @param userRoleList 用户与角色关联信息列表
     */
    void saveBatch(@Param("list") List<UserRole> userRoleList);

    /**
     * 添加关联
     * @param userRole 关联信息
     */
    void save(@Param("userRole") UserRole userRole);

    /**
     * 根据用户ID修改关联角色ID
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void updateRoleId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    /**
     * 批量删除用户与角色的关联信息
     * @param userIds 用户id列表
     */
    void deleteBatchUserIds(@Param("userIds") List<Integer> userIds);
}
