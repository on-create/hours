package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.common.constant.EntityConstant;
import com.example.hours.dao.ActivityDao;
import com.example.hours.dao.RegisterActivityDao;
import com.example.hours.entity.Activity;
import com.example.hours.entity.RegisterActivity;
import com.example.hours.exception.RegisterFailedException;
import com.example.hours.service.RegisterActivityService;
import com.example.hours.utils.CommonUtils;
import com.example.hours.model.vo.RegisterActivityVo;
import com.example.hours.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service("registerActivityService")
public class RegisterActivityServiceImpl extends ServiceImpl<RegisterActivityDao, RegisterActivity> implements RegisterActivityService {

    /**
     * 存储锁对象
     */
    private static final Map<Integer, Object> lock_map = new ConcurrentHashMap<>();

    @Autowired
    private ActivityDao activityDao;

    /**
     * 根据活动id获取锁对象
     * @param key 活动id
     * @return 锁对象
     */
    private Object getLockObject(Integer key) {
        Object lock = lock_map.get(key);
        if (Objects.nonNull(lock)) {
            return lock;
        }

        // 没有对应的锁对象，则自己创建
        Object newLock = new Object();
        Object lockObj = lock_map.putIfAbsent(key, newLock);
        // 如果 lockObj 是 null，说明 lockObj 是存储的锁，否则 lockObj 是其他线程插入的锁
        return Objects.isNull(lockObj) ? newLock : lockObj;
    }

    @Transactional
    @Override
    public void registerActivity(RegisterActivity registerActivity) {
        // TODO 判断报名用户信誉度是否被允许报名活动
        //throw new RegisterFailedException("信誉度不足");

        Object lock = getLockObject(registerActivity.getActivityId());
        synchronized (lock) {
            // 获取活动信息
            Activity activity = activityDao.selectOne(
                    new LambdaQueryWrapper<Activity>()
                            .eq(Activity::getId, registerActivity.getActivityId())
                            .select(Activity::getNumber, Activity::getMaximum,
                                    Activity::getApplyStartTime, Activity::getApplyEndTime)
            );
            if (activity.getApplyStartTime().isAfter(CommonUtils.getNowTime())) {
                throw new RegisterFailedException("报名还未开始");
            }

            if (activity.getApplyEndTime().isBefore(CommonUtils.getNowTime())) {
                throw new RegisterFailedException("报名已结束");
            }

            if (activity.getMaximum() != EntityConstant.ACTIVITY_NUMBER_UNLIMITED && activity.getNumber() >= activity.getMaximum()) {
                throw new RegisterFailedException("报名人数已满");
            }
            // 更新报名人数
            Activity updateActivity = Activity.builder()
                    .id(registerActivity.getActivityId())
                    .number(activity.getNumber() + 1)
                    .build();
            activityDao.updateById(updateActivity);
        }
        registerActivity.setUserId(SecurityUtils.getUserId());
        this.save(registerActivity);
    }

    /**
     * 获取当前用户报名的所有活动id
     * @return 活动id列表
     */
    @Override
    public List<Integer> getActivityIds() {
        LambdaQueryWrapper<RegisterActivity> queryWrapper = new LambdaQueryWrapper<RegisterActivity>()
                .eq(RegisterActivity::getUserId, SecurityUtils.getUserId())
                .select(RegisterActivity::getActivityId);
        // 当前用户报名的所有活动
        List<RegisterActivity> registerActivities = this.baseMapper.selectList(queryWrapper);
        return registerActivities.stream()
                .map(RegisterActivity::getActivityId).collect(Collectors.toList());
    }

    @Override
    public Integer getRegisterId(Integer activityId) {
        LambdaQueryWrapper<RegisterActivity> queryWrapper = new LambdaQueryWrapper<RegisterActivity>()
                .eq(RegisterActivity::getActivityId, activityId)
                .eq(RegisterActivity::getUserId, SecurityUtils.getUserId())
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

    @Override
    public Boolean isSign(Integer activityId) {
        Integer userId = SecurityUtils.getUserId();
        System.out.println(activityId);
        System.out.println("userId: " + userId);
        RegisterActivity registerActivity = this.baseMapper.selectOne(
                new LambdaQueryWrapper<RegisterActivity>()
                        .eq(RegisterActivity::getActivityId, activityId)
                        .eq(RegisterActivity::getUserId, userId)
                        .select(RegisterActivity::getId)
        );
        return Objects.nonNull(registerActivity);
    }
}
