package com.example.hours.controller;

import com.example.hours.bo.ResourceBO;
import com.example.hours.common.Result;
import com.example.hours.service.ResourceService;
import com.example.hours.vo.ResourceModuleVO;
import com.example.hours.vo.ResourceVO;
import com.example.hours.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 资源控制类
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 查看资源模块列表
     * @return {@link Result<List>}
     */
    @GetMapping("/module_list")
    public Result<List<ResourceModuleVO>> getModules() {
        List<ResourceModuleVO> res = resourceService.getModules();
        return Result.success(res);
    }

    /**
     * 查看某个模块的资源列表
     * @param moduleId 模块id
     * @return {@link Result<List>}
     */
    // TODO 后期考虑分页
    @GetMapping("/module/resource_list")
    public Result<List<ResourceVO>> getModuleChildren(@RequestParam("moduleId") Integer moduleId) {
        List<ResourceVO> res = resourceService.getModuleChildren(moduleId);
        return Result.success(res);
    }

    /**
     * 获取具有资源访问权限的角色列表
     * @param resourceId 资源id
     * @return {@link Result<List>}
     */
    @GetMapping("/module/resource_role")
    public Result<List<RoleVO>> getResourceRoleVO(@RequestParam("resourceId") Integer resourceId) {
        List<RoleVO> res = resourceService.selectRoleVOByResourceId(resourceId);
        return Result.success(res);
    }

    // TODO 针对某个请求更新可访问角色功能


}
