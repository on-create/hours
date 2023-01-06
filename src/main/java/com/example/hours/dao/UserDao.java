package com.example.hours.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hours.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
