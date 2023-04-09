package com.example.hours.mapper;

import com.example.hours.entity.UserRole;
import com.example.hours.model.vo.AuthUserVO;
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

    /**
     * 根据角色ID获取关联的用户数量
     * @param roleId 角色ID
     * @return 用户数量
     */
    int selectCountByRoleId(@Param("roleId") Integer roleId);

    /**
     * 根据角色ID获取关联的角色ID（分页）
     * @param roleId 角色ID
     * @param skip 跳过条数
     * @param limit 每页条数
     * @return 用户ID列表
     */
    List<Integer> selectPageByRoleId(@Param("roleId") Integer roleId, @Param("skip") Integer skip, @Param("size") Integer limit);

    /**
     * 批量更新角色ID
     * @param authUserVO 授权用户信息
     */
    void updateRoleIdByUserIds(@Param("authUserVO") AuthUserVO authUserVO);
}
