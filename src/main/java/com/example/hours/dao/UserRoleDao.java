package com.example.hours.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hours.entity.sys.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleDao extends BaseMapper<UserRole> {

    /**
     * 通过用户ID删除用户和角色关联
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserRoleByUserId(@Param("userId") Integer userId);

    /**
     * 批量删除用户和角色关联
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchUserRole(@Param("userIds") List<Integer> userIds);

    /**
     * 通过角色ID查询角色使用数量
     * @param roleId 角色ID
     * @return 结果
     */
    int countUserRoleByRoleId(@Param("roleId") Integer roleId);

    /**
     * 批量新增用户角色信息
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    int addBatchUserRole(@Param("list") List<UserRole> userRoleList);

    /**
     * 删除用户和角色关联信息
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    int deleteUserRoleInfo(@Param("userRole") UserRole userRole);

    /**
     * 批量取消授权用户角色
     * @param roleId 角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    int deleteUserRoleInfos(@Param("roleId") Integer roleId, @Param("userIds") List<Integer> userIds);
}
