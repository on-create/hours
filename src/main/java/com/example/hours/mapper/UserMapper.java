package com.example.hours.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hours.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
