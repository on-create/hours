package com.example.hours.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hours.entity.Activity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActivityDao extends BaseMapper<Activity> {

}
