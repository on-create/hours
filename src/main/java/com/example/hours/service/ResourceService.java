package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.Resource;
import com.example.hours.model.vo.ResourceModuleVO;
import com.example.hours.model.vo.ResourceVO;
import com.example.hours.model.vo.RoleVO;

import java.util.List;

public interface ResourceService extends IService<Resource> {

    List<ResourceModuleVO> getModules();

    List<ResourceVO> getModuleChildren(Integer moduleId);

    List<RoleVO> selectRoleVOByResourceId(Integer resourceId);

    void deleteModuleById(Integer moduleId);

    void saveModule(ResourceModuleVO resourceModuleVO);

    void updateModule(ResourceModuleVO resourceModuleVO);

    void saveResource(Resource resource);

    void updateResource(Resource resource);

    void deleteResource(Integer resourceId);
}
