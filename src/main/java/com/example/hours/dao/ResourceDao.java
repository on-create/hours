package com.example.hours.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hours.model.bo.ResourceRoleBO;
import com.example.hours.entity.Resource;
import com.example.hours.model.vo.RoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResourceDao extends BaseMapper<Resource> {

    List<ResourceRoleBO> listResourceRoles();

    List<RoleVO> selectRoleVOByResourceId(@Param("resourceId") Integer resourceId);
}
