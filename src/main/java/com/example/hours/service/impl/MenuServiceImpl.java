package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.common.constant.CommonConstant;
import com.example.hours.dao.MenuDao;
import com.example.hours.dao.RoleDao;
import com.example.hours.dao.RoleMenuDao;
import com.example.hours.entity.sys.SysRole;
import com.example.hours.entity.sys.SysUser;
import com.example.hours.model.TreeSelect;
import com.example.hours.entity.sys.Menu;
import com.example.hours.entity.sys.RoleMenu;
import com.example.hours.exception.HourException;
import com.example.hours.exception.WarnException;
import com.example.hours.service.MenuService;
import com.example.hours.utils.HolderUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleMenuDao roleMenuDao;

    /**
     * 根据用户查询系统菜单列表
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<Menu> selectMenuList(Integer userId) {
        return selectMenuList(new Menu(), userId);
    }

    @Override
    public List<Menu> selectMenuList(Menu menu, Integer userId) {
        List<Menu> menuList;
        // 如果是管理员，则显示所有菜单
        if (SysUser.isAdmin(userId)) {
            menuList = this.baseMapper.selectMenuList(menu);
        } else {
            menuList = this.baseMapper.selectMenuListByUserId(menu, userId);
        }
        return menuList;
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public Menu selectMenuById(Integer menuId) {
        return this.baseMapper.selectMenuById(menuId);
    }

    /**
     * 构建前端所需要下拉树结构
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<Menu> menus) {
        List<Menu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream()
                .map(TreeSelect::new)
                .collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     * @param menus 菜单列表
     * @return 树结构列表
     */
    @Override
    public List<Menu> buildMenuTree(List<Menu> menus) {
        // 菜单id列表
        List<Integer> ids = menus.stream().map(Menu::getId).collect(Collectors.toList());
        List<Menu> res = menus.stream()
                // 过滤出父菜单
                .filter(menu -> !ids.contains(menu.getParentId()))
                // 递归每一个父菜单的子菜单节点
                .peek(menu -> recursion(menus, menu))
                .collect(Collectors.toList());

        if (res.isEmpty()) {
            res = menus;
        }
        return res;
    }

    /**
     * 根据角色ID查询菜单树信息
     * @param roleId 角色ID
     * @return 被选中菜单的id列表
     */
    @Override
    public List<Integer> selectMenuListByRoleId(Integer roleId) {
        SysRole sysRole = roleDao.selectRoleById(roleId);
        return this.baseMapper.selectMenuListByRoleId(roleId, sysRole.isMenuCheckStrictly());
    }

    /**
     * 校验菜单名称是否唯一
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public boolean checkMenuNameUnique(Menu menu) {
        // TODO 校验 menuName 和 parentId 不能为空
        Menu info = this.baseMapper.selectOne(
                new LambdaQueryWrapper<Menu>()
                        .eq(Menu::getParentId, menu.getParentId())
                        .eq(Menu::getName, menu.getName())
                        .select(Menu::getId)
        );
        if (!Objects.isNull(info)) {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }

    /**
     * 新增保存菜单信息
     * @param menu 菜单信息
     */
    @Override
    public void addMenu(Menu menu) {
        if (!checkMenuNameUnique(menu)) {
            throw new HourException("新增菜单'" + menu.getName() + "'失败，菜单名称已存在");
        }
        menu.setCreateBy(HolderUserUtils.getLoginUserName());
        this.save(menu);
    }

    /**
     * 修改保存菜单信息
     * @param menu 菜单信息
     */
    @Override
    public void updateMenuById(Menu menu) {
        // TODO id, menuName, parentId 不能为空
        if (!checkMenuNameUnique(menu)) {
            throw new HourException("修改菜单'" + menu.getName() + "'失败，菜单名称已存在");
        }

        if (menu.getId().equals(menu.getParentId())) {
            throw new HourException("修改菜单'" + menu.getName() + "'失败，不能选择自己作为上级菜单");
        }
        menu.setUpdateBy(HolderUserUtils.getLoginUserName());
        this.updateById(menu);
    }

    /**
     * 删除菜单管理信息
     * @param menuId 菜单ID
     */
    @Override
    public void deleteMenuById(Integer menuId) {
        if (hasChildByMenuId(menuId)) {
            throw new WarnException("存在子菜单,不允许删除");
        }

        if (checkMenuExistRole(menuId)) {
            throw new WarnException("菜单已分配,不允许删除");
        }
        this.baseMapper.deleteById(menuId);
    }

    /**
     * 根据角色ID查询权限
     * @param roleId 角色ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByRoleId(Integer roleId) {
        List<String> perms = this.baseMapper.selectMenuPermsByRoleId(roleId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询权限
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Integer userId) {
        List<String> perms = this.baseMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 是否存在菜单子节点
     * @param menuId 菜单ID
     * @return 结果
     */
    private boolean hasChildByMenuId(Integer menuId) {
        int result = this.baseMapper.selectCount(
                new LambdaQueryWrapper<Menu>()
                        .eq(Menu::getParentId, menuId)
        );
        return result > 0;
    }

    /**
     * 查询菜单使用数量
     * @param menuId 菜单ID
     * @return 结果
     */
    private boolean checkMenuExistRole(Integer menuId) {
        int result = roleMenuDao.selectCount(
                new LambdaQueryWrapper<RoleMenu>()
                        .eq(RoleMenu::getMenuId, menuId)
        );
        return result > 0;
    }

    /**
     * 递归列表
     * @param menus 菜单列表
     * @param parentMenu 父菜单
     */
    private void recursion(List<Menu> menus, Menu parentMenu) {
        // 得到子节点列表
        List<Menu> childList = getChildList(menus, parentMenu);
        parentMenu.setChildren(childList);
        for (Menu child : childList) {
            if (hasChild(menus, child)) {
                recursion(menus, child);
            }
        }
    }

    /**
     * 获取子节点列表
     * @param menus 菜单列表
     * @param parentMenu 父菜单
     * @return 子菜单列表
     */
    private List<Menu> getChildList(List<Menu> menus, Menu parentMenu) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(parentMenu.getId()))
                .collect(Collectors.toList());
    }

    /**
     * 判断当前菜单是否有子菜单
     * @param menus 菜单列表
     * @param curMenu 当前菜单
     * @return 是否有子菜单
     */
    private boolean hasChild(List<Menu> menus, Menu curMenu) {
        long count = menus.stream()
                .filter(menu -> menu.getParentId().equals(curMenu.getId()))
                .count();
        return count > 0;
    }

    /**
     * 根据用户ID查询菜单
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<Menu> selectMenuTreeByUserId(Integer userId) {
        List<Menu> menus;
        if (HolderUserUtils.isAdmin(userId)) {
            menus = this.baseMapper.selectMenuTreeAll();
        } else {
            menus = this.baseMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus);
    }

    /**
     * 根据父节点的ID获取所有子节点
     * @param menuList 分类表
     * @return String
     */
    private List<Menu> getChildPerms(List<Menu> menuList) {
        List<Menu> res = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getParentId() == 0) {
                recursion(menuList, menu);
                res.add(menu);
            }
        }
        return res;
    }

    /**
     * 构建前端路由所需要的菜单
     * @param menus 菜单列表
     * @return 路由列表
     */
    /*@Override
    public List<RouterVO> buildMenus(List<Menu> menus) {
        List<RouterVO> routers = new LinkedList<RouterVO>();
        for (Menu menu : menus) {
            RouterVO router = new RouterVO();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQuery());
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
            List<SysMenu> cMenus = menu.getChildren();
            if (StringUtils.isNotEmpty(cMenus) && UserConstants.TYPE_DIR.equals(menu.getMenuType()))
            {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            else if (isMenuFrame(menu))
            {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
                children.setQuery(menu.getQuery());
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            else if (menu.getParentId().intValue() == 0 && isInnerLink(menu))
            {
                router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }*/
}
