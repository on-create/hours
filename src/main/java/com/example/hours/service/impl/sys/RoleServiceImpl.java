package com.example.hours.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.common.constant.CommonConstant;
import com.example.hours.dao.RoleDao;
import com.example.hours.dao.RoleMenuDao;
import com.example.hours.dao.UserRoleDao;
import com.example.hours.entity.sys.*;
import com.example.hours.exception.HourException;
import com.example.hours.service.PermissionService;
import com.example.hours.service.sys.RoleService;
import com.example.hours.utils.HolderUserUtils;
import com.example.hours.utils.page.PageParams;
import com.example.hours.utils.page.PageResult;
import com.example.hours.utils.page.PageUtils;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, SysRole> implements RoleService {

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private PermissionService permissionService;

    /*@Override
    public List<RoleVO> getRoleVOs(Integer status) {
        List<SysRole> sysRoles = this.baseMapper.selectList(
                new LambdaQueryWrapper<SysRole>()
                        .eq(SysRole::getStatus, status)
                        .select(SysRole::getId, SysRole::getRoleName, SysRole::getRoleKey)
        );

        return sysRoles.stream()
                .map(role -> {
                    RoleVO roleVO = new RoleVO();
                    BeanUtils.copyProperties(role, roleVO);
                    return roleVO;
                }).collect(Collectors.toList());
    }

    @Override
    public void saveOrUpdateRole(RoleVO roleVO) {
        // 保存
        if (Objects.nonNull(roleVO.getStatus())) {
            SysRole role = SysRole.builder()
                    .roleName(roleVO.getName())
                    .roleKey(roleVO.getRoleKey())
                    .status(EntityConstant.COMMON_ENABLE)
                    .delFlag(EntityConstant.COMMON_UNDELETED)
                    .build();
            this.save(role);
            return;
        }

        // 更新
        SysRole role = new SysRole();
        BeanUtils.copyProperties(roleVO, role);
        this.updateById(role);
    }

    @Override
    public void deleteRoles(List<Integer> roleIdList) {
        // 判断角色下是否有用户
        Integer count = this.baseMapper.selectUserCount(roleIdList);
        if (count > 0) {
            throw new HourException("该角色下存在用户");
        }
        this.baseMapper.deleteBatchIds(roleIdList);
    }*/

    /**
     * 根据条件分页查询角色数据
     * @param sysRole 角色信息
     * @param params 分页信息
     * @return 角色数据集合信息
     */
    @Override
    public PageResult selectRoleList(SysRole sysRole, PageParams params) {
        IPage<?> page = PageUtils.initPage(params);
        List<SysRole> sysRoles = this.baseMapper.selectRoleList(page, sysRole);
        return PageUtils.selectResult(page, sysRoles);
    }

    /**
     * 校验角色是否有数据权限
     * @param roleId 角色id
     */
    @Override
    public void checkRoleDataScope(Integer roleId) {
        if (!SysUser.isAdmin(HolderUserUtils.getLoginUserId())) {
            SysRole sysRole = new SysRole();
            sysRole.setId(roleId);
            // TODO 查找当前用户所能查看的所有角色列表 sysRoles
            List<SysRole> sysRoles = null;
            if (Collections.isEmpty(sysRoles)) {
                throw new HourException("没有权限访问角色数据！");
            }
        }
    }

    /**
     * 通过角色ID查询角色
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRole selectRoleById(Integer roleId) {
        return this.baseMapper.selectOne(
                new LambdaQueryWrapper<SysRole>()
                        .eq(SysRole::getId, roleId)
                        .select(
                                SysRole::getId,
                                SysRole::getRoleName,
                                SysRole::getRoleKey,
                                SysRole::getRoleSort,
                                SysRole::getDataScope,
                                SysRole::isMenuCheckStrictly,
                                SysRole::getStatus,
                                SysRole::getDelFlag,
                                SysRole::getCreateTime,
                                SysRole::getRemark
                        )
        );
    }

    /**
     * 新增保存角色信息
     * @param sysRole 角色信息
     */
    @Transactional
    @Override
    public void insertRole(SysRole sysRole) {
        if (!checkRoleNameUnique(sysRole)) {
            throw new HourException("新增角色'" + sysRole.getRoleName() + "'失败，角色名称已存在");
        }

        if (!checkRoleKeyUnique(sysRole)) {
            throw new HourException("新增角色'" + sysRole.getRoleName() + "'失败，角色权限已存在");
        }
        sysRole.setCreateBy(HolderUserUtils.getLoginUserName());
        this.save(sysRole);
        this.insertRoleMenu(sysRole);
    }

    /**
     * 修改角色信息
     * @param sysRole 角色信息
     */
    @Transactional
    @Override
    public void updateRole(SysRole sysRole) {
        // 校验当前角色是否能被修改
        checkRoleAllowed(sysRole);
        checkRoleDataScope(sysRole.getId());
        if (!checkRoleNameUnique(sysRole)) {
            throw new HourException("修改角色'" + sysRole.getRoleName() + "'失败，角色名称已存在");
        }

        if (!checkRoleKeyUnique(sysRole)) {
            throw new HourException("修改角色'" + sysRole.getRoleName() + "'失败，角色权限已存在");
        }
        sysRole.setUpdateBy(HolderUserUtils.getLoginUserName());

        this.updateById(sysRole);
        // 删除角色与菜单的关联
        roleMenuDao.deleteRoleMenuByRoleId(sysRole.getId());
        insertRoleMenu(sysRole);

        // 更新缓存用户权限
        LoginUser loginUser = HolderUserUtils.getLoginUser();
        if (Objects.nonNull(loginUser.getSysUser()) && !loginUser.getSysUser().isAdmin()) {
            loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getSysUser()));
            // TODO 更新登录用户信息，刷新token
            //loginUser.setSysUser(userService.selectUserByUserName(loginUser.getSysUser().getUserName()));
            //tokenService.setLoginUser(loginUser);
        }
    }

    /**
     * 根据用户ID查询权限
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Integer userId) {
        // TODO 待修改
        List<SysRole> sysRoles = this.baseMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole sysRole : sysRoles) {
            // TODO: 待修改
            if (Objects.nonNull(sysRole.getRoleKey())) {
                permsSet.addAll(Arrays.asList(sysRole.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 修改数据权限信息
     * @param sysRole 角色信息
     */
    // TODO：后续考虑修改
    @Override
    public void updateAuthDataScope(SysRole sysRole) {
        // 校验角色是否允许被修改
        checkRoleAllowed(sysRole);
        checkRoleDataScope(sysRole.getId());

        // 修改角色信息
        this.updateById(sysRole);
        /*// 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(sysRole.getRoleId());
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(sysRole);*/
    }

    /**
     * 修改角色状态
     * @param sysRole 角色信息
     */
    @Override
    public void updateRoleStatus(SysRole sysRole) {
        checkRoleAllowed(sysRole);
        checkRoleDataScope(sysRole.getId());
        sysRole.setUpdateBy(HolderUserUtils.getLoginUserName());
    }

    /**
     * 批量删除角色信息
     * @param roleIds 需要删除的角色ID
     */
    @Transactional
    @Override
    public void deleteRoleByIds(List<Integer> roleIds) {
        for (Integer roleId : roleIds) {
            checkRoleAllowed(new SysRole(roleId));
            checkRoleDataScope(roleId);
            SysRole sysRole = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new HourException(String.format("%1$s已分配,不能删除", sysRole.getRoleName()));
            }
        }
        // 删除角色与菜单关联
        roleMenuDao.deleteBatchRoleMenu(roleIds);
        this.baseMapper.deleteBatchIds(roleIds);
    }

    /**
     * 获取所有角色信息
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRolesAll() {
        return this.baseMapper.selectList(
                new LambdaQueryWrapper<SysRole>()
                        .select(
                                SysRole::getId,
                                SysRole::getRoleName,
                                SysRole::getRoleKey,
                                SysRole::getRoleSort,
                                SysRole::getDataScope,
                                SysRole::isMenuCheckStrictly,
                                SysRole::getStatus,
                                SysRole::getCreateTime,
                                SysRole::getRemark
                        ).orderByAsc(SysRole::getRoleSort)
        );
    }

    /**
     * 通过角色ID查询角色与用户的关联数量
     * @param roleId 角色ID
     * @return 结果
     */
    private int countUserRoleByRoleId(Integer roleId) {
        return userRoleDao.countUserRoleByRoleId(roleId);
    }

    /**
     * 校验角色是否允许被操作
     * @param sysRole 角色信息
     */
    private void checkRoleAllowed(SysRole sysRole) {
        if (Objects.nonNull(sysRole.getId()) && sysRole.isAdmin()) {
            throw new HourException("不允许修改超级管理员角色");
        }
    }

    /**
     * 新增角色菜单信息
     * @param sysRole 角色对象
     */
    private void insertRoleMenu(SysRole sysRole) {
        List<RoleMenu> list = new ArrayList<>();
        // 创建角色与菜单的所有关联信息
        for (Integer menuId : sysRole.getMenuIds()) {
            list.add(
                    RoleMenu.builder()
                            .roleId(sysRole.getId())
                            .menuId(menuId)
                            .build()
            );
        }

        // 批量插入
        if (list.size() > 0) {
            roleMenuDao.addBatchRoleMenu(list);
        }
    }

    /**
     * 校验角色名称是否唯一
     * @param sysRole 角色信息
     * @return 结果
     */
    private boolean checkRoleNameUnique(SysRole sysRole) {
        SysRole info = this.baseMapper.selectOne(
                new LambdaQueryWrapper<SysRole>()
                        .eq(SysRole::getRoleName, sysRole.getRoleName())
                        .select(SysRole::getId)
        );
        if (Objects.nonNull(info)) {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     * @param sysRole 角色信息
     * @return 结果
     */
    private boolean checkRoleKeyUnique(SysRole sysRole) {
        SysRole info = this.baseMapper.selectOne(
                new LambdaQueryWrapper<SysRole>()
                        .eq(SysRole::getRoleKey, sysRole.getRoleKey())
                        .select(SysRole::getId)
        );
        if (Objects.nonNull(info)) {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }

    /**
     * 根据用户ID查询角色
     * @param userId 用户id
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleByUserId(Integer userId) {
        List<SysRole> userSysRoles = this.baseMapper.selectRolePermissionByUserId(userId);
        List<SysRole> sysRoles = selectRolesAll();
        for (SysRole sysRole : sysRoles) {
            for (SysRole userSysRole : userSysRoles) {
                if (sysRole.getId().longValue() == userSysRole.getId().longValue()) {
                    sysRole.setFlag(true);
                    break;
                }
            }
        }
        return sysRoles;
    }

    /**
     * 取消授权用户角色
     * @param userRole 用户和角色关联信息
     */
    @Override
    public void deleteAuthUser(UserRole userRole) {
        userRoleDao.deleteUserRoleInfo(userRole);
    }

    /**
     * 批量取消授权用户角色
     * @param roleId 角色ID
     * @param userIds 需要取消授权的用户数据ID
     */
    @Override
    public void deleteAuthUsers(Integer roleId, List<Integer> userIds) {
        userRoleDao.deleteUserRoleInfos(roleId, userIds);
    }

    /**
     * 批量选择授权用户角色
     * @param roleId 角色ID
     * @param userIds 需要授权的用户数据ID
     */
    @Override
    public void insertAuthUsers(Integer roleId, List<Integer> userIds) {
        checkRoleDataScope(roleId);
        // 新增用户与角色管理
        List<UserRole> list = userIds.stream()
                .map(userId -> UserRole.builder().userId(userId).roleId(roleId).build())
                .collect(Collectors.toList());
        userRoleDao.addBatchUserRole(list);
    }
}
