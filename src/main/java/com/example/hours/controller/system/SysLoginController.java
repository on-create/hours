package com.example.hours.controller.system;

import com.example.hours.common.Result;
import com.example.hours.entity.sys.Menu;
import com.example.hours.entity.sys.SysUser;
import com.example.hours.service.LoginService;
import com.example.hours.model.vo.LoginUserVO;
import com.example.hours.service.MenuService;
import com.example.hours.service.PermissionService;
import com.example.hours.utils.HolderUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 登录控制类
 */
@RestController
@RequestMapping("/sys")
public class SysLoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 登录
     * @param loginUserVo 用户vo信息
     * @return {@link Result<Map>}
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Validated @RequestBody LoginUserVO loginUserVo) {
        Map<String, Object> map = loginService.login(loginUserVo);
        return Result.success(map, "登录成功");
    }

    /**
     * 退出
     * @return {@link Result<>}
     */
    @PostMapping("/my_logout")
    public Result<Object> logout() {
        loginService.logout();
        return Result.success("退出成功");
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public Result<Map<String, Object>> getInfo() {
        SysUser sysUser = HolderUserUtils.getLoginUser().getSysUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(sysUser);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(sysUser);
        Map<String, Object> data = new HashMap<>();
        data.put("user", sysUser);
        data.put("roles", roles);
        data.put("permissions", permissions);
        return Result.success(data);
    }

    /**
     * 获取路由信息
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public Result getRouters() {
        Integer userId = HolderUserUtils.getLoginUserId();
        List<Menu> menus = menuService.selectMenuTreeByUserId(userId);
        //return AjaxResult.success(menuService.buildMenus(menus));
        return Result.success();
    }
}
