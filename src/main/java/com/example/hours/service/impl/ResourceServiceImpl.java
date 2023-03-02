package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.common.constant.EntityConstant;
import com.example.hours.dao.ResourceDao;
import com.example.hours.entity.Resource;
import com.example.hours.exception.HourException;
import com.example.hours.service.ResourceService;
import com.example.hours.vo.ResourceModuleVO;
import com.example.hours.vo.ResourceVO;
import com.example.hours.vo.RoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    @Override
    public void deleteModuleById(Integer moduleId) {
        // 获取模块下资源数量
        Integer count = this.baseMapper.selectCount(
                new LambdaQueryWrapper<Resource>()
                        .eq(Resource::getParentId, moduleId)
        );

        if (count > 0) {
            throw new HourException("模块下存在资源，删除模块失败");
        }
        this.baseMapper.deleteById(moduleId);
    }

    @Override
    public void saveModule(ResourceModuleVO resourceModuleVO) {
        Resource module = Resource.builder()
                .name(resourceModuleVO.getName())
                .parentId(EntityConstant.RESOURCE_MODULE_ID)
                .isAnonymous(EntityConstant.RESOURCE_NOT_ANONYMOUS)
                .status(EntityConstant.COMMON_ENABLE)
                .delFlag(EntityConstant.COMMON_UNDELETED)
                .build();
        this.save(module);
    }

    @Transactional
    @Override
    public void updateModule(ResourceModuleVO resourceModuleVO) {
        // 更新启用状态，同时更新模块下资源的启用状态
        if (Objects.nonNull(resourceModuleVO.getStatus())) {
            // 1.获取模块下资源的id列表
            List<Resource> resources = this.baseMapper.selectList(
                    new LambdaQueryWrapper<Resource>()
                            .eq(Resource::getParentId, resourceModuleVO.getId())
                            .select(Resource::getId)
            );

            // 2.批量修改资源的启用状态
            resources.forEach(resource -> resource.setStatus(resourceModuleVO.getStatus()));
            this.updateBatchById(resources);
        }

        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceModuleVO, resource);
        this.updateById(resource);
    }

    @Override
    public void saveResource(Resource resource) {
        if (Objects.isNull(resource.getIsAnonymous())) {
            // 默认不允许匿名访问
            resource.setIsAnonymous(EntityConstant.RESOURCE_NOT_ANONYMOUS);
        }
        resource.setStatus(EntityConstant.COMMON_ENABLE);
        resource.setDelFlag(EntityConstant.COMMON_UNDELETED);
        this.save(resource);
    }

    // TODO 确定表结构后再修改
    @Override
    public void updateResource(Resource resource) {
        this.updateById(resource);
    }

    // TODO 确定表结构后再修改
    @Override
    public void deleteResource(Integer resourceId) {
        this.baseMapper.deleteById(resourceId);
    }

}
