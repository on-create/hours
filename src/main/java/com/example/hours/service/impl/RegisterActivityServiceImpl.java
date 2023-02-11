package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.common.constant.CommonConstant;
import com.example.hours.common.constant.EntityConstant;
import com.example.hours.common.enums.ZoneEnum;
import com.example.hours.dao.RegisterActivityDao;
import com.example.hours.entity.RegisterActivity;
import com.example.hours.exception.RegisterFailedException;
import com.example.hours.service.RegisterActivityService;
import com.example.hours.utils.page.PageUtils;
import com.example.hours.utils.page.Query;
import com.example.hours.vo.RegisterActivityVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service("registerActivityService")
public class RegisterActivityServiceImpl extends ServiceImpl<RegisterActivityDao, RegisterActivity> implements RegisterActivityService {

    @Override
    public void registerActivity(RegisterActivityVo registerActivityVo) {
        // TODO 判断报名用户信誉度是否被允许报名活动
        //throw new RegisterFailedException("信誉度不足");

        if (registerActivityVo.getNumber() >= registerActivityVo.getMaximum()) {
            throw new RegisterFailedException("报名人数已满");
        }

        if (registerActivityVo.getApplyStartTime().isAfter(LocalDateTime.now(ZoneId.of(ZoneEnum.SHANGHAI.getZone())))) {
            throw new RegisterFailedException("报名还未开始");
        }

        if (registerActivityVo.getApplyEndTime().isBefore(LocalDateTime.now(ZoneId.of(ZoneEnum.SHANGHAI.getZone())))) {
            throw new RegisterFailedException("报名已结束");
        }

        // TODO 更换为当前用户id
        RegisterActivity registerActivity = RegisterActivity.builder()
                .userId(1)
                .activityId(registerActivityVo.getId())
                .status(EntityConstant.REGISTER_ACTIVITY_ABSENT)
                .build();
        this.save(registerActivity);
    }

    @Override
    public List<Integer> getActivityIds() {
        // TODO 替换用户id
        LambdaQueryWrapper<RegisterActivity> queryWrapper = new LambdaQueryWrapper<RegisterActivity>()
                .eq(RegisterActivity::getUserId, 1)
                .select(RegisterActivity::getActivityId);
        // 当前用户报名的所有活动
        List<RegisterActivity> registerActivities = this.baseMapper.selectList(queryWrapper);
        return registerActivities.stream()
                .map(RegisterActivity::getActivityId).collect(Collectors.toList());
    }
}
