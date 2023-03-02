package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.common.validator.group.AddGroup;
import com.example.hours.common.validator.group.UpdateGroup;
import com.example.hours.entity.Resource;
import com.example.hours.service.ResourceService;
import com.example.hours.vo.ResourceModuleVO;
import com.example.hours.vo.ResourceVO;
import com.example.hours.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 添加资源模块
     * @param resourceModuleVO 资源模块
     * @return {@link Result<>}
     */
    @PostMapping("/module/save")
    public Result<Object> saveModule(@Validated(AddGroup.class) @RequestBody ResourceModuleVO resourceModuleVO) {
        resourceService.saveModule(resourceModuleVO);
        return Result.success();
    }

    /**
     * 更新资源模块
     * @param resourceModuleVO 资源模块
     * @return {@link Result<>}
     */
    @PostMapping("/module/update")
    public Result<Object> updateModule(@Validated(UpdateGroup.class) @RequestBody ResourceModuleVO resourceModuleVO) {
        resourceService.updateModule(resourceModuleVO);
        return Result.success();
    }

    /**
     * 通过 id 删除资源模块
     * @param moduleId 模块id
     * @return {@link Result<>}
     */
    @PostMapping("/module/delete")
    public Result<Object> deleteModule(@RequestParam("moduleId") Integer moduleId) {
        resourceService.deleteModuleById(moduleId);
        return Result.success();
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
     * 添加资源
     * @param resource 资源
     * @return {@link Result<>}
     */
    @PostMapping("/save")
    public Result<Object> saveResource(@Validated(AddGroup.class) @RequestBody Resource resource) {
        resourceService.saveResource(resource);
        return Result.success();
    }

    /**
     * 更新资源信息
     * @param resource 资源
     * @return {@link Result<>}
     */
    @PostMapping("/update")
    public Result<Object> updateResource(@Validated(UpdateGroup.class) @RequestBody Resource resource) {
        resourceService.updateResource(resource);
        return Result.success();
    }

    /**
     * 删除资源
     * @param resourceId 资源id
     * @return {@link Result<>}
     */
    @PostMapping("/delete")
    public Result<Object> deleteResource(@RequestParam("resourceId") Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.success();
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
