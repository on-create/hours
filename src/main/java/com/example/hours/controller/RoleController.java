package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.entity.Role;
import com.example.hours.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色控制类
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

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
     * 添加角色
     * @param roleVO 角色
     * @return {@link Result<Object>}
     */
    /*@PostMapping("/save_or_update")
    public Result<Object> saveOrUpdateRole(@RequestBody RoleVO roleVO) {
        roleService.saveOrUpdateRole(roleVO);
        return Result.success();
    }*/

    /**
     * 批量删除角色
     * @param roleIdList 角色id列表
     * @return {@link Result<Object>}
     */
    /*@PostMapping("/delete")
    public Result<Object> deleteRoles(@RequestBody List<Integer> roleIdList) {
        roleService.deleteRoles(roleIdList);
        return Result.success();
    }*/
}
