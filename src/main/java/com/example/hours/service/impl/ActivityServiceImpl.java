package com.example.hours.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.dao.ActivityDao;
import com.example.hours.entity.Activity;
import com.example.hours.service.ActivityService;
import org.springframework.stereotype.Service;

@Service("activityService")
public class ActivityServiceImpl extends ServiceImpl<ActivityDao, Activity> implements ActivityService {
}
