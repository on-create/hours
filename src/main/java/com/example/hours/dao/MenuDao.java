package com.example.hours.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hours.entity.Menu;
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
}
