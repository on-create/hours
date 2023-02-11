package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.WelfareHour;
import com.example.hours.utils.page.PageUtils;

import java.util.Map;

public interface WelfareHoursService extends IService<WelfareHour> {

    WelfareHour getHourInfoByYear(String year);

    PageUtils getHourList(Map<String, Object> params);
}
