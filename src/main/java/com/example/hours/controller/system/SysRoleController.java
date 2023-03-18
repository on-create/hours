package com.example.hours.controller.system;

import com.example.hours.common.Result;
import com.example.hours.entity.sys.SysRole;
import com.example.hours.entity.sys.SysUser;
import com.example.hours.entity.sys.UserRole;
import com.example.hours.model.pagination.RolePage;
import com.example.hours.service.sys.RoleService;
import com.example.hours.service.sys.UserService;
import com.example.hours.utils.page.PageParams;
import com.example.hours.utils.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制类
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @PreAuthorize("@perms.hasPermission('system:role:list')")
    @GetMapping("/list")
    public Result<PageResult> list(RolePage rolePage) {
        // TODO 待删除
        System.out.println(rolePage.toString());
        PageParams pageParams = new PageParams(rolePage.getCurrPage(), rolePage.getLimit());
        SysRole sysRole = SysRole.builder()
                .id(rolePage.getRoleId())
                .roleName(rolePage.getRoleName())
                .roleKey(rolePage.getRoleKey())
                .status(rolePage.getRoleStatus())
                .build();
        PageResult page = roleService.selectRoleList(sysRole, pageParams);
        return Result.success(page);
    }

    /*@PreAuthorize("@perms.hasPermission('system:role:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysRole role) {
        List<SysRole> list = roleService.selectRoleList(role);
        ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
        util.exportExcel(response, list, "角色数据");
    }*/

    /**
     * 根据角色编号获取详细信息
     * @param roleId 角色ID
     * @return 角色详细信息
     */
    @PreAuthorize("@perms.hasPermission('system:role:query')")
    @GetMapping(value = "/{roleId}")
    public Result<SysRole> getInfo(@PathVariable Integer roleId) {
        roleService.checkRoleDataScope(roleId);
        SysRole sysRole = roleService.selectRoleById(roleId);
        return Result.success(sysRole);
    }

    /**
     * 新增角色
     * @param sysRole 角色信息
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:role:add')")
    @PostMapping("/add")
    public Result<?> add(@Validated @RequestBody SysRole sysRole) {
        roleService.insertRole(sysRole);
        return Result.success();

    }

    /**
     * 修改角色信息
     * @param sysRole 角色信息
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:role:edit')")
    @PostMapping("/update")
    public Result<?> edit(@Validated @RequestBody SysRole sysRole) {
        roleService.updateRole(sysRole);
        return Result.success();
    }

    /**
     * 修改保存数据权限
     * @param sysRole 角色信息
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:role:edit')")
    @PostMapping("/dataScope")
    public Result<?> updateDataScope(@RequestBody SysRole sysRole) {
        roleService.updateAuthDataScope(sysRole);
        return Result.success();
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@perms.hasPermission('system:role:edit')")
    @PostMapping("/changeStatus")
    public Result<?> changeStatus(@RequestBody SysRole sysRole) {
        roleService.updateRoleStatus(sysRole);
        return Result.success();
    }

    /**
     * 删除角色
     * @param roleIds 角色id列表
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:role:remove')")
    @PostMapping("/remove")
    public Result<?> remove(@RequestParam List<Integer> roleIds) {
        roleService.deleteRoleByIds(roleIds);
        return Result.success();
    }

    /**
     * 获取角色选择框列表
     */
    @PreAuthorize("@perms.hasPermission('system:role:query')")
    @GetMapping("/option_select")
    public Result<List<SysRole>> optionSelect() {
        List<SysRole> sysRoles = roleService.selectRolesAll();
        return Result.success(sysRoles);
    }

    /**
     * 查询已分配用户角色列表
     */
    @PreAuthorize("@perms.hasPermission('system:role:list')")
    @GetMapping("/authUser/allocatedList")
    public Result<PageResult> allocatedList(@RequestBody SysUser sysUser, @RequestBody PageParams params) {
        PageResult result = userService.selectAllocatedList(params, sysUser);
        return Result.success(result);
    }

    /**
     * 查询未分配用户角色列表
     * @param sysUser 用户信息
     * @param params 分页信息
     * @return
     */
    @PreAuthorize("@perms.hasPermission('system:role:list')")
    @GetMapping("/authUser/unallocatedList")
    public Result<PageResult> unallocatedList(@RequestBody SysUser sysUser, @RequestBody PageParams params) {
        PageResult result = userService.selectUnallocatedList(params, sysUser);
        return Result.success(result);
    }

    /**
     * 取消授权用户
     * @param userRole 用户与角色关联信息
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:role:edit')")
    @PostMapping("/authUser/cancel")
    public Result<?> cancelAuthUser(@RequestBody UserRole userRole) {
        roleService.deleteAuthUser(userRole);
        return Result.success();
    }

    /**
     * 批量取消授权用户
     * @param roleId 角色id
     * @param userIds 用户id列表
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:role:edit')")
    @PostMapping("/authUser/cancelAll")
    public Result<?> cancelAuthUserAll(@RequestParam("roleId") Integer roleId, @RequestParam("userIds") List<Integer> userIds) {
        roleService.deleteAuthUsers(roleId, userIds);
        return Result.success();
    }

    /**
     * 批量选择用户授权
     * @param roleId 角色id
     * @param userIds 用户id列表
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:role:edit')")
    @PostMapping("/authUser/selectAll")
    public Result<?> selectAuthUserAll(@RequestParam("roleId") Integer roleId, @RequestParam("userIds") List<Integer> userIds) {
        roleService.insertAuthUsers(roleId, userIds);
        return Result.success();
    }
}
