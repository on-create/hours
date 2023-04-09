package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.entity.Role;
import com.example.hours.entity.UserRole;
import com.example.hours.model.pagination.UserPage;
import com.example.hours.model.vo.AuthUserVO;
import com.example.hours.model.vo.StatusVO;
import com.example.hours.model.pagination.RolePage;
import com.example.hours.service.RoleService;
import com.example.hours.service.UserService;
import com.example.hours.utils.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制类
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    /**
     * 根据角色ID获取角色信息
     * @param roleId 角色ID
     * @return 角色信息
     */
    @GetMapping("/info")
    public Result<Role> getRoleInfo(@RequestParam("roleId") Integer roleId) {
        Role role = roleService.getRoleInfoById(roleId);
        return Result.success(role);
    }

    /**
     * 获取角色列表
     * @return {@link Result<List>}
     */
    @GetMapping("/all")
    public Result<List<Role>> getAllRoles() {
        List<Role> allRoles = roleService.getAllRoles();
        return Result.success(allRoles);
    }

    /**
     * 分页获取角色信息列表
     * @param rolePage 分页请求信息
     * @return 角色列表信息
     */
    @GetMapping("/list")
    public Result<PageResult> getRoles(RolePage rolePage) {
        PageResult result = roleService.getRoleInfos(rolePage);
        return Result.success(result);
    }

    /**
     * 添加角色
     * @param role 角色信息
     * @return {@link Result}
     */
    @PostMapping("/add")
    @PreAuthorize("@permission.hasRole('admin')")
    public Result<?> addRole(@RequestBody Role role) {
        roleService.insertRole(role);
        return Result.success();
    }

    /**
     * 删除角色
     * @param roleIds 角色id列表
     * @return {@link Result}
     */
    @PostMapping("/delete")
    @PreAuthorize("@permission.hasRole('admin')")
    public Result<?> deleteRoles(@RequestBody List<Integer> roleIds) {
        roleService.deleteRoles(roleIds);
        return Result.success();
    }

    /**
     * 更新角色状态
     * @param statusVO 角色状态信息
     * @return {@link Result}
     */
    @PostMapping("/changeStatus")
    @PreAuthorize("@permission.hasRole('admin')")
    public Result<?> changeStatus(@RequestBody StatusVO statusVO) {
        roleService.changeStatus(statusVO);
        return Result.success();
    }

    /**
     * 已分配某角色的用户分页列表
     * @param userPage 查询信息
     * @return 分页列表
     */
    @GetMapping("/authUser/allocatedList")
    public Result<PageResult> allocatedList(UserPage userPage) {
        PageResult result = userService.selectAllocatedList(userPage);
        return Result.success(result);
    }

    /**
     * 未分配某角色的用户分页列表
     * @param userPage 查询信息
     * @return 分页列表
     */
    @GetMapping("/authUser/unallocatedList")
    public Result<PageResult> unallocatedList(UserPage userPage) {
        PageResult result = userService.selectUnallocatedList(userPage);
        return Result.success(result);
    }

    /**
     * 取消授权用户
     * @param userRole 用户与角色关联信息
     * @return {@link Result<>}
     */
    @PostMapping("/authUser/cancel")
    @PreAuthorize("@permission.hasRole('admin')")
    public Result<?> cancelAuthUser(@RequestBody UserRole userRole) {
        roleService.cancelAuthUser(userRole);
        return Result.success();
    }

    /**
     * 批量取消授权用户
     * @param authUserVO 授权用户信息
     * @return {@link Result<>}
     */
    @PostMapping("/authUser/cancelAll")
    @PreAuthorize("@permission.hasRole('admin')")
    public Result<?> cancelAuthUserAll(@RequestBody AuthUserVO authUserVO) {
        roleService.cancelAuthUserAll(authUserVO);
        return Result.success();
    }

    /**
     * 批量选择用户授权
     * @param authUserVO 授权用户信息
     * @return {@link Result<>}
     */
    @PostMapping("/authUser/selectAll")
    @PreAuthorize("@permission.hasRole('admin')")
    public Result<?> selectAuthUserAll(@RequestBody AuthUserVO authUserVO) {
        roleService.insertAuthUsers(authUserVO);
        return Result.success();
    }
}
