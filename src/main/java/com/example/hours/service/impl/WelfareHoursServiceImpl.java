package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.dao.WelfareHoursDao;
import com.example.hours.entity.WelfareHour;
import com.example.hours.service.WelfareHoursService;
import com.example.hours.utils.CommonUtils;
import com.example.hours.utils.page.PageUtils;
import com.example.hours.utils.page.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("welfareHours")
public class WelfareHoursServiceImpl extends ServiceImpl<WelfareHoursDao, WelfareHour> implements WelfareHoursService {

    @Override
    public WelfareHour getHourInfoByYear(String year) {
        // TODO 从上下文获取当前登录用户id，作为查询条件之一
        if (StringUtils.isBlank(year)) {
            year = CommonUtils.getAcademicYear();
        }
        return this.getOne(
                new LambdaQueryWrapper<WelfareHour>()
                        .eq(WelfareHour::getYear, year)
                        .eq(WelfareHour::getUserId, 1)
                        .select(WelfareHour::getHours)
        );
    }

    @Override
    public PageUtils getHourList(Map<String, Object> params) {
        // TODO 获取当前用户id，作为查询条件
        IPage<WelfareHour> page = this.page(
                new Query<WelfareHour>().getPage(params),
                new LambdaQueryWrapper<WelfareHour>()
                        .eq(WelfareHour::getUserId, 1)
                        .select(WelfareHour::getHours, WelfareHour::getYear)
                        .orderByDesc(WelfareHour::getCreateTime)
        );
        return new PageUtils(page);
    }
}
