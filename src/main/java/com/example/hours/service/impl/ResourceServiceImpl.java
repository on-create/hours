package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.common.constant.EntityConstant;
import com.example.hours.dao.ResourceDao;
import com.example.hours.entity.Resource;
import com.example.hours.exception.HourException;
import com.example.hours.service.ResourceService;
import com.example.hours.model.vo.ResourceModuleVO;
import com.example.hours.model.vo.ResourceVO;
import com.example.hours.model.vo.RoleVO;
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
        // ???????????????????????????
        Integer count = this.baseMapper.selectCount(
                new LambdaQueryWrapper<Resource>()
                        .eq(Resource::getParentId, moduleId)
        );

        if (count > 0) {
            throw new HourException("??????????????????????????????????????????");
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
        // ???????????????????????????????????????????????????????????????
        if (Objects.nonNull(resourceModuleVO.getStatus())) {
            // 1.????????????????????????id??????
            List<Resource> resources = this.baseMapper.selectList(
                    new LambdaQueryWrapper<Resource>()
                            .eq(Resource::getParentId, resourceModuleVO.getId())
                            .select(Resource::getId)
            );

            // 2.?????????????????????????????????
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
            // ???????????????????????????
            resource.setIsAnonymous(EntityConstant.RESOURCE_NOT_ANONYMOUS);
        }
        resource.setStatus(EntityConstant.COMMON_ENABLE);
        resource.setDelFlag(EntityConstant.COMMON_UNDELETED);
        this.save(resource);
    }

    // TODO ???????????????????????????
    @Override
    public void updateResource(Resource resource) {
        this.updateById(resource);
    }

    // TODO ???????????????????????????
    @Override
    public void deleteResource(Integer resourceId) {
        this.baseMapper.deleteById(resourceId);
    }

}
