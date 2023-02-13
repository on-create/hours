package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.common.enums.ZoneEnum;
import com.example.hours.dao.RegisterActivityDao;
import com.example.hours.entity.RegisterActivity;
import com.example.hours.exception.RegisterFailedException;
import com.example.hours.service.RegisterActivityService;
import com.example.hours.utils.CommonUtils;
import com.example.hours.vo.RegisterActivityVo;
import org.springframework.stereotype.Service;

import java.util.List;
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

        if (registerActivityVo.getApplyStartTime().isAfter(CommonUtils.getNowTime())) {
            throw new RegisterFailedException("报名还未开始");
        }

        if (registerActivityVo.getApplyEndTime().isBefore(CommonUtils.getNowTime())) {
            throw new RegisterFailedException("报名已结束");
        }

        // TODO 更换为当前用户id
        RegisterActivity registerActivity = RegisterActivity.builder()
                .userId(1)
                .activityId(registerActivityVo.getId())
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

    @Override
    public Integer getRegisterId(Integer activityId) {
        // TODO 获取当前用户id，作为查询条件之一
        LambdaQueryWrapper<RegisterActivity> queryWrapper = new LambdaQueryWrapper<RegisterActivity>()
                .eq(RegisterActivity::getActivityId, activityId)
                .eq(RegisterActivity::getUserId, 1)
                .select(RegisterActivity::getId);
        RegisterActivity registerActivity = this.getOne(queryWrapper);
        if (registerActivity == null) {
            throw new RuntimeException("活动不存在");
        }
        return registerActivity.getId();
    }

    @Override
    public void updateSignIn(Integer registerId) {
        RegisterActivity registerActivity = RegisterActivity.builder()
                .id(registerId)
                .signInTime(CommonUtils.getNowTime())
                .build();
        this.updateById(registerActivity);
    }

    @Override
    public void updateSignOut(Integer registerId) {
        RegisterActivity registerActivity = RegisterActivity.builder()
                .id(registerId)
                .signOutTime(CommonUtils.getNowTime())
                .build();
        this.updateById(registerActivity);
    }
}
