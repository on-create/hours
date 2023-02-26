package com.example.hours.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hours.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao extends BaseMapper<Role> {

    List<String> selectRoleKeysByUserId(@Param("userId") Integer userId);

    Integer selectUserCount(@Param("roleIdList") List<Integer> roleIdList);
}
