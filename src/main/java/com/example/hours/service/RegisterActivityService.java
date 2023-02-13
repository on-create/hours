package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.RegisterActivity;
import com.example.hours.vo.RegisterActivityVo;

import java.util.List;

public interface RegisterActivityService extends IService<RegisterActivity> {

    void registerActivity(RegisterActivityVo registerActivityVo);

    List<Integer> getActivityIds();

    Integer getRegisterId(Integer activityId);

    void updateSignIn(Integer registerId);

    void updateSignOut(Integer registerId);
}
