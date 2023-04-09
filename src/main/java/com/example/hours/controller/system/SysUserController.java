package com.example.hours.controller.system;

import com.example.hours.common.Result;
import com.example.hours.entity.sys.SysRole;
import com.example.hours.entity.sys.SysUser;
import com.example.hours.model.pagination.UserPage;
import com.example.hours.service.sys.UserService;
import com.example.hours.utils.page.PageParams;
import com.example.hours.utils.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制类
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     * @param userPage 请求参数
     * @return 用户列表
     */
    @PreAuthorize("@perms.hasPermission('system:user:list')")
    @GetMapping("/list")
    public Result<PageResult> list(UserPage userPage) {
        // TODO 待删除
        System.out.println(userPage.toString());
        PageParams pageParams = new PageParams(userPage.getCurrPage(), userPage.getLimit());
        SysUser sysUser = SysUser.builder()
                .username(userPage.getUsername())
                .phone(userPage.getPhone())
                .status(userPage.getStatus())
                .build();
        PageResult result = userService.selectUserList(pageParams, sysUser);
        return Result.success(result);
    }

    /*@PreAuthorize("@perms.hasPermission('system:user:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.exportExcel(response, list, "用户数据");
    }*/

    /*@PreAuthorize("@perms.hasPermission('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return success(message);
    }*/

    /*@PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }*/

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@perms.hasPermission('system:user:query')")
    @GetMapping(value = { "/", "/{userId}" })
    public Result<SysUser> getInfo(@PathVariable(value = "userId", required = false) Integer userId) {
        SysUser sysUser = userService.selectUser(userId);
        return Result.success(sysUser);
    }

    /**
     * 新增用户
     * @param sysUser 用户信息
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:user:add')")
    @PostMapping("/add")
    public Result<?> add(@Validated @RequestBody SysUser sysUser) {
        userService.insertUser(sysUser);
        return Result.success();
    }

    /**
     * 修改用户信息
     * @param sysUser 用户信息
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:user:edit')")
    @PostMapping("/update")
    public Result<?> edit(@Validated @RequestBody SysUser sysUser) {
        userService.updateUser(sysUser);
        return Result.success();
    }

    /**
     * 删除用户
     * @param userIds 用户id列表
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:user:remove')")
    @PostMapping("/delete")
    public Result<?> remove(@RequestParam List<Integer> userIds) {
        userService.deleteUserByIds(userIds);
        return Result.success();
    }

    /**
     * 重置密码
     * @param sysUser 用户信息
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:user:resetPwd')")
    @PostMapping("/resetPwd")
    public Result<?> resetPwd(@RequestBody SysUser sysUser) {
        userService.resetPassword(sysUser);
        return Result.success();
    }

    /**
     * 修改用户状态
     * @param sysUser 用户信息
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:user:edit')")
    @PostMapping("/changeStatus")
    public Result<?> changeStatus(@RequestBody SysUser sysUser) {
        userService.updateUserStatus(sysUser);
        return Result.success();
    }

    /**
     * 根据用户编号获取授权角色
     * @param userId 用户编号
     * @return 用户角色列表
     */
    @PreAuthorize("@perms.hasPermission('system:user:query')")
    @GetMapping("/authRole/{userId}")
    public Result<List<SysRole>> authRole(@PathVariable("userId") Integer userId) {
        List<SysRole> sysRoles = userService.getRolesById(userId);
        return Result.success(sysRoles);
    }

    /**
     * 给用户授权角色
     * @param userId 用户id
     * @param roleIds 角色id列表
     * @return {@link Result<>}
     */
    @PreAuthorize("@perms.hasPermission('system:user:edit')")
    @PostMapping("/authRole")
    public Result<?> insertAuthRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") List<Integer> roleIds) {
        userService.insertUserAuth(userId, roleIds);
        return Result.success();
    }
}
