package com.example.hours.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hours.entity.WelfareHour;
import com.example.hours.model.bo.UserHour;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WelfareHourMapper extends BaseMapper<WelfareHour> {

    /**
     * 分页获取学时信息列表
     * @param iPage 分页信息
     * @param userHour 搜索信息
     * @return 用户学时信息列表
     */
    List<UserHour> getUserHours(IPage<UserHour> iPage, @Param("userHour") UserHour userHour);
}
