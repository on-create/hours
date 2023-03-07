package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.domain.TreeSelect;
import com.example.hours.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {

    /**
     * 根据用户查询系统菜单列表
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<Menu> selectMenuList(Integer userId);

    /**
     * 根据用户查询系统菜单列表
     * @param menu 菜单信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<Menu> selectMenuList(Menu menu, Integer userId);

    /**
     * 根据菜单ID查询信息
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    Menu selectMenuById(Integer menuId);

    /**
     * 构建前端所需要下拉树结构
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    List<TreeSelect> buildMenuTreeSelect(List<Menu> menus);

    /**
     * 构建前端所需要树结构
     * @param menus 菜单列表
     * @return 树结构列表
     */
    List<Menu> buildMenuTree(List<Menu> menus);

    /**
     * 根据角色ID查询菜单树信息
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<Integer> selectMenuListByRoleId(Integer roleId);

    /**
     * 校验菜单名称是否唯一
     * @param menu 菜单信息
     * @return true：唯一  false：不唯一
     */
    boolean checkMenuNameUnique(Menu menu);

    /**
     * 新增菜单
     * @param menu 菜单信息
     */
    void addMenu(Menu menu);

    /**
     * 修改菜单
     * @param menu 菜单信息
     */
    void updateMenuById(Menu menu);

    /**
     * 删除菜单
     * @param menuId 菜单id
     */
    void deleteMenuById(Integer menuId);
}
