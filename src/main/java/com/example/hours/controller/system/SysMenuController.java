package com.example.hours.controller.system;

import com.example.hours.common.Result;
import com.example.hours.model.TreeSelect;
import com.example.hours.entity.sys.Menu;
import com.example.hours.service.MenuService;
import com.example.hours.utils.HolderUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单控制类
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单列表
     * @param menu 菜单搜索关键信息
     * @return {@link Result<List>}
     */
    @PreAuthorize("@perms.hasPermission('system:menu:list')")
    @GetMapping("/list")
    public Result<List<Menu>> list(Menu menu) {
        List<Menu> menus = menuService.selectMenuList(menu, HolderUserUtils.getLoginUserId());
        return Result.success(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     * @param menuId 菜单id
     * @return 菜单信息
     */
    @PreAuthorize("@perms.hasPermission('system:menu:query')")
    @GetMapping(value = "/{menuId}")
    public Result<Menu> getMenuInfo(@PathVariable("menuId") Integer menuId) {
        Menu menu = menuService.selectMenuById(menuId);
        return Result.success(menu);
    }

    /**
     * 获取菜单下拉树列表
     * @param menu 菜单
     * @return 菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public Result<List<TreeSelect>> getTreeSelect(@RequestBody Menu menu) {
        List<Menu> menus = menuService.selectMenuList(menu, HolderUserUtils.getLoginUserId());
        List<TreeSelect> treeSelectList = menuService.buildMenuTreeSelect(menus);
        return Result.success(treeSelectList);
    }

    /**
     * 加载对应角色菜单列表树
     * @param roleId 角色id
     * @return 对应角色菜单列表树
     */
    // TODO bug
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public Result<Map<String, Object>> roleMenuTreeselect(@PathVariable("roleId") Integer roleId) {
        System.out.println(roleId);
        List<Menu> menus = menuService.selectMenuList(HolderUserUtils.getLoginUserId());
        Map<String, Object> data = new HashMap<>();
        data.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        data.put("menus", menuService.buildMenuTreeSelect(menus));
        return Result.success(data);
    }

    /**
     * 新增菜单
     * @param menu 菜单信息
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:menu:add')")
    @PostMapping("/add")
    public Result<?> add(@Validated @RequestBody Menu menu) {
        menuService.addMenu(menu);
        return Result.success();
    }

    /**
     * 修改菜单
     * @param menu 菜单信息
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:menu:edit')")
    @PostMapping("/update")
    public Result<?> edit(@Validated @RequestBody Menu menu) {
        menuService.updateMenuById(menu);
        return Result.success();
    }

    /**
     * 删除菜单
     * @param menuId 菜单id
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:menu:remove')")
    @DeleteMapping("/{menuId}")
    public Result<?> remove(@PathVariable("menuId") Integer menuId) {
        menuService.deleteMenuById(menuId);
        return Result.success();
    }
}
