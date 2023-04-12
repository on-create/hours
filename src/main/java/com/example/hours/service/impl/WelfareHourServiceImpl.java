package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.mapper.WelfareHourMapper;
import com.example.hours.entity.WelfareHour;
import com.example.hours.model.bo.UserHour;
import com.example.hours.model.pagination.HourPage;
import com.example.hours.service.WelfareHourService;
import com.example.hours.utils.CommonUtils;
import com.example.hours.utils.page.PageResult;
import com.example.hours.utils.page.PageUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service("welfareHours")
public class WelfareHourServiceImpl extends ServiceImpl<WelfareHourMapper, WelfareHour> implements WelfareHourService {

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
    public PageResult getHourList(Map<String, Object> params) {
        // TODO 获取当前用户id，作为查询条件
        // TODO 修改分页参数
        /*IPage<WelfareHour> page = this.page(
                new Query<WelfareHour>().getPage(params),
                new LambdaQueryWrapper<WelfareHour>()
                        .eq(WelfareHour::getUserId, 1)
                        .select(WelfareHour::getHours, WelfareHour::getYear)
                        .orderByDesc(WelfareHour::getCreateTime)
        );*/
        IPage<WelfareHour> page = null;
        return new PageResult(page);
    }

    /**
     * 分页获取学时信息列表
     * @param hourPage 学时信息（分页）
     * @return
     */
    @Override
    public PageResult getUserHours(HourPage hourPage) {
        IPage<UserHour> iPage = PageUtils.initPage(hourPage);
        UserHour userHour = UserHour.builder()
                .name(hourPage.getName())
                .classId(hourPage.getClassId())
                .year(hourPage.getYear())
                .build();
        List<UserHour> userHours = this.baseMapper.getUserHours(iPage, userHour);
        return PageUtils.selectResult(iPage, userHours);
    }

    /**
     * 删除学时信息
     * @param hourIds 学时id列表
     */
    @Override
    public void deleteHours(List<Integer> hourIds) {
        this.baseMapper.deleteBatchIds(hourIds);
    }
}
