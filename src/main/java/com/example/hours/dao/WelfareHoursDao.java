package com.example.hours.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hours.entity.WelfareHour;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WelfareHoursDao extends BaseMapper<WelfareHour> {
}
