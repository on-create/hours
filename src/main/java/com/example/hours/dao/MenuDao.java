package com.example.hours.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hours.entity.sys.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuDao extends BaseMapper<Menu> {

    List<Menu> selectMenuList(@Param("menu") Menu menu);

    List<Menu> selectMenuListByUserId(@Param("menu") Menu menu, @Param("userId") Integer userId);

    Menu selectMenuById(@Param("menuId") Integer menuId);

    /**
     * 根据角色ID查询菜单树信息
     * @param roleId 角色ID
     * @param menuCheckStrictly 菜单树选择项是否关联显示
     * @return 被选中菜单列表
     */
    List<Integer> selectMenuListByRoleId(@Param("roleId") Integer roleId, @Param("menuCheckStrictly") boolean menuCheckStrictly);

    /**
     * 根据角色ID查询权限
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 根据用户ID查询权限
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByUserId(@Param("userId") Integer userId);

    /**
     * 查询全部菜单
     * @return 菜单列表
     */
    List<Menu> selectMenuTreeAll();

    /**
     * 根据用户ID查询菜单
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<Menu> selectMenuTreeByUserId(@Param("userId") Integer userId);
}
