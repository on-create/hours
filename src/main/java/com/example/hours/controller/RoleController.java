package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.service.RoleService;
import com.example.hours.model.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制类
 */
//@RestController
//@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查看角色列表
     * @param status 角色状态（是否被禁用）
     * @return {@link Result<List>}
     */
    /*@GetMapping("/list")
    public Result<List<RoleVO>> getRoleVOList(@RequestParam("status") Integer status) {
        List<RoleVO> res = roleService.getRoleVOs(status);
        return Result.success(res);
    }*/

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
