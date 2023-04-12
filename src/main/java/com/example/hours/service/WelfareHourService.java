package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.WelfareHour;
import com.example.hours.model.pagination.HourPage;
import com.example.hours.utils.page.PageResult;

import java.util.List;
import java.util.Map;

public interface WelfareHourService extends IService<WelfareHour> {

    WelfareHour getHourInfoByYear(String year);

    PageResult getHourList(Map<String, Object> params);

    /**
     * 分页获取学时信息列表
     * @param hourPage 学时信息（分页）
     * @return 学时信息列表
     */
    PageResult getUserHours(HourPage hourPage);

    /**
     * 删除学时信息
     * @param hourIds 学时id列表
     */
    void deleteHours(List<Integer> hourIds);
}
