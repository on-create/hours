package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.dao.ResourceDao;
import com.example.hours.entity.Resource;
import com.example.hours.service.ResourceService;
import com.example.hours.vo.ResourceModuleVO;
import com.example.hours.vo.ResourceVO;
import com.example.hours.vo.RoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("resourceService")
public class ResourceServiceImpl extends ServiceImpl<ResourceDao, Resource> implements ResourceService {

    @Override
    public List<ResourceModuleVO> getModules() {
        LambdaQueryWrapper<Resource> queryWrapper = new LambdaQueryWrapper<Resource>()
                .isNull(Resource::getParentId)
                .select(Resource::getId, Resource::getName);
        List<Resource> resources = this.baseMapper.selectList(queryWrapper);

        return resources.stream()
                .map(resource -> {
                    ResourceModuleVO resourceModuleVO = new ResourceModuleVO();
                    BeanUtils.copyProperties(resource, resourceModuleVO);
                    return resourceModuleVO;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ResourceVO> getModuleChildren(Integer moduleId) {
        List<Resource> resources = this.baseMapper.selectList(
                new LambdaQueryWrapper<Resource>()
                        .eq(Resource::getParentId, moduleId)
                        .select(
                                Resource::getId,
                                Resource::getName,
                                Resource::getUrl,
                                Resource::getRequestMethod,
                                Resource::getStatus,
                                Resource::getIsAnonymous
                        )
        );

        return resources.stream()
                .map(resource -> {
                    ResourceVO resourceVO = new ResourceVO();
                    BeanUtils.copyProperties(resource, resourceVO);
                    return resourceVO;
                }).collect(Collectors.toList());
    }

    @Override
    public List<RoleVO> selectRoleVOByResourceId(Integer resourceId) {
        return this.baseMapper.selectRoleVOByResourceId(resourceId);
    }

}
