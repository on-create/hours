package com.example.hours.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hours.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    Set<String> getRoleKeyByUserId(@Param("userId") Integer userId);
}
