package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.RegisterActivity;
import com.example.hours.model.vo.RegisterActivityVo;

import java.util.List;

public interface RegisterActivityService extends IService<RegisterActivity> {

    void registerActivity(RegisterActivity registerActivity);

    /**
     * 获取当前用户报名的所有活动id
     * @return 活动id列表
     */
    List<Integer> getActivityIds();

    Integer getRegisterId(Integer activityId);

    void updateSignIn(Integer registerId);

    void updateSignOut(Integer registerId);

    /**
     * 根据活动id判断用户是否已报名
     * @param activityId 活动id
     * @return 是否已报名
     */
    Boolean isSign(Integer activityId);
}
