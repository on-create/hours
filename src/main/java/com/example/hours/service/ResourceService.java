package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.Resource;
import com.example.hours.vo.ResourceModuleVO;
import com.example.hours.vo.ResourceVO;
import com.example.hours.vo.RoleVO;

import java.util.List;

public interface ResourceService extends IService<Resource> {

    List<ResourceModuleVO> getModules();

    List<ResourceVO> getModuleChildren(Integer moduleId);

    List<RoleVO> selectRoleVOByResourceId(Integer resourceId);
}
