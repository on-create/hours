package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.common.constant.CommonConstant;
import com.example.hours.common.constant.EntityConstant;
import com.example.hours.common.enums.ZoneEnum;
import com.example.hours.dao.ActivityDao;
import com.example.hours.entity.Activity;
import com.example.hours.exception.DraftException;
import com.example.hours.exception.TimeValidException;
import com.example.hours.service.ActivityService;
import com.example.hours.service.RegisterActivityService;
import com.example.hours.utils.CommonUtils;
import com.example.hours.utils.page.PageResult;
import com.example.hours.model.vo.ActivityVo;
import com.example.hours.model.vo.SimpleActivityVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("activityService")
public class ActivityServiceImpl extends ServiceImpl<ActivityDao, Activity> implements ActivityService {

    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @Autowired
    private RegisterActivityService registerActivityService;

    /**
     * 保存新增活动 或 草稿添加内容后更新数据
     * @param activity 活动
     */
    @Override
    public void saveOrUpdateActivity(Activity activity) {
        // TODO 获取当前用户 id，根据 id 查询用户，判断是否有权限申办活动，如果有则将 id 设置进 activity 中

        // 校验报名时间和活动开始时间
        isValid(activity);
        applyOrDraft(activity);
        // 根据活动 id 获取活动状态
        Integer activityId = activity.getId();
        if (activityId != null) {
            Activity draft = this.getOne(
                    new QueryWrapper<Activity>()
                            .eq("id", activityId)
                            .select("applicant_id", "status")
            );

            if (draft != null) {
                Integer status = draft.getStatus();
                Integer applicantId = draft.getApplicantId();
                // TODO 添加用户 id 判断 applicantId == userId
                boolean flag = (status != null && applicantId != null && status == EntityConstant.ACTIVITY_Draft);
                // 更新草稿
                if (flag) {
                    LocalDateTime now = LocalDateTime.now(ZoneId.of(ZoneEnum.SHANGHAI.getZone()));
                    activity.setStatus(EntityConstant.ACTIVITY_PENDING_APPROVAL);
                    activity.setCreateTime(now);
                    activity.setUpdateTime(now);
                    this.updateById(activity);
                    return;
                }
            }
        }

        // 设置状态为待审核
        activity.setStatus(EntityConstant.ACTIVITY_PENDING_APPROVAL);
        this.save(activity);
    }

    /**
     * 校验报名时间和活动开始时间
     * @param activity 活动
     */
    private void isValid(Activity activity) {
        LocalDateTime applyStart = activity.getApplyStartTime();
        LocalDateTime applyEnd = activity.getApplyEndTime();
        LocalDateTime activityStart = activity.getActivityStartTime();
        LocalDateTime activityEnd = activity.getActivityEndTime();
        if (applyStart.isAfter(applyEnd)) {
            throw new TimeValidException("报名开始时间不能晚于报名结束时间");
        }

        if (activityStart.isAfter(activityEnd)) {
            throw new TimeValidException("活动开始时间不能晚于活动结束时间");
        }

        if (applyStart.isAfter(activityStart)) {
            throw new TimeValidException("报名开始时间不能晚于活动开始时间");
        }

        if (applyEnd.isAfter(activityEnd)) {
            throw new TimeValidException("报名结束时间不能晚于活动结束时间");
        }
    }

    @Override
    public void saveDraft(Activity activity) {
        // TODO 获取当前用户 id，根据 id 查询用户，判断是否有权限申办活动，如果有则将 id 设置进 activity 中

        // 检查 activity 对象存在值，如果都为空，直接返回
        if (CommonUtils.checkObjAllFieldsIsNull(activity)) {
            return;
        }

        // 根据 id 查草稿数量
        // TODO 更换成用户 id
        int count = this.baseMapper.getDraftCountByUserId(1);
        if (count == EntityConstant.ACTIVITY_DRAFT_MAX) {
            throw new DraftException("草稿数不能大于" + EntityConstant.ACTIVITY_DRAFT_MAX);
        }
        applyOrDraft(activity);
        // 设置状态为草稿
        activity.setStatus(EntityConstant.ACTIVITY_Draft);
        this.save(activity);
    }

    /**
     * 申请活动或保存草稿时设置报名人数和最大人数限制
     * @param activity 活动
     */
    private void applyOrDraft(Activity activity) {
        // 报名人数为 0
        activity.setNumber(EntityConstant.ACTIVITY_NUMBER_ZERO);
        // 最大报名人数限制，不设置则默认无限制
        if (activity.getMaximum() == null) {
            activity.setMaximum(EntityConstant.ACTIVITY_NO_MAXIMUM);
        }
    }

    @Override
    public void updateActivity(Activity activity) {
        this.updateById(activity);
    }

    @Override
    public PageResult getActivityListByStatus(Map<String, Object> params) {
        // TODO 获取当前用户 id，作为查询条件之一
        LambdaQueryWrapper<Activity> queryWrapper = new LambdaQueryWrapper<Activity>().select(
                Activity::getId,
                Activity::getNumber,
                Activity::getMaximum,
                Activity::getPicture,
                Activity::getTheme,
                Activity::getAddress,
                Activity::getApplyStartTime,
                Activity::getApplyEndTime,
                Activity::getActivityStartTime,
                Activity::getActivityEndTime
        );

        String statusStr = (String) params.get("status");
        if (StringUtils.isNotBlank(statusStr)) {
            int status = Integer.parseInt(statusStr);
            // 草稿
            if (status == EntityConstant.ACTIVITY_Draft) {
                queryWrapper.eq(Activity::getStatus, EntityConstant.ACTIVITY_Draft);
            }

            // 待审批
            if (status == EntityConstant.ACTIVITY_PENDING_APPROVAL) {
                queryWrapper.eq(Activity::getStatus, EntityConstant.ACTIVITY_PENDING_APPROVAL);
            }

            // 审批通过
            if (status == EntityConstant.ACTIVITY_APPROVE) {
                queryWrapper.eq(Activity::getStatus, EntityConstant.ACTIVITY_APPROVE);
            }
        }

        /*IPage<Activity> page = this.page(
                new Query<Activity>().getPage(params),
                queryWrapper
        );*/
        // TODO 分页修改
        IPage<Activity> page = null;
        PageResult pageResult = new PageResult(page);

        List<Activity> activityList = page.getRecords();
        List<SimpleActivityVo> simpleActivityVoList = activityList.stream().map(activity -> {
            SimpleActivityVo simpleActivityVo = new SimpleActivityVo();
            BeanUtils.copyProperties(activity, simpleActivityVo);
            return simpleActivityVo;
        }).collect(Collectors.toList());

        pageResult.setList(simpleActivityVoList);
        return pageResult;
    }

    @Override
    public ActivityVo getActivityVo(Integer id) {
        Activity activity = this.getOne(
                new LambdaQueryWrapper<Activity>().eq(Activity::getId, id)
                        .select(
                                Activity::getId,
                                Activity::getNumber,
                                Activity::getMaximum,
                                Activity::getPicture,
                                Activity::getTheme,
                                Activity::getAddress,
                                Activity::getSignIn,
                                Activity::getSignOut,
                                Activity::getIntroduction,
                                Activity::getReward,
                                Activity::getApplyStartTime,
                                Activity::getApplyEndTime,
                                Activity::getActivityStartTime,
                                Activity::getActivityEndTime
                        )
        );
        ActivityVo activityVo = new ActivityVo();
        BeanUtils.copyProperties(activity, activityVo);
        return activityVo;
    }

    @Override
    public void deleteDraft(Integer id) {
        this.baseMapper.delete(
                new QueryWrapper<Activity>()
                        .eq("id", id)
                        .eq("status", EntityConstant.ACTIVITY_Draft)
        );
    }

    @Override
    public void updateDraft(Activity activity) {
        if (CommonUtils.checkObjAllFieldsIsNull(activity)) {
            return;
        }

        int count = this.count(
                new QueryWrapper<Activity>()
                        .eq("id", activity.getId())
                        // TODO 替换为当前用户 id
                        .eq("applicant_id", 1)
        );
        if (count == 1) {
            // 执行更新
            this.updateById(activity);
        }
    }

    @Override
    public PageResult getMyActivityList(Map<String, Object> params) {
        List<Integer> activityIds = registerActivityService.getActivityIds();
        LambdaQueryWrapper<Activity> queryWrapper = new LambdaQueryWrapper<Activity>().select(
                Activity::getId,
                Activity::getNumber,
                Activity::getMaximum,
                Activity::getPicture,
                Activity::getTheme,
                Activity::getAddress,
                Activity::getApplyStartTime,
                Activity::getApplyEndTime,
                Activity::getActivityStartTime,
                Activity::getActivityEndTime
        ).in(Activity::getId, activityIds);

        String activityStatusStr = (String) params.get("activityStatus");
        if (StringUtils.isNotBlank(activityStatusStr)) {
            // 获取当前格式化时间
            LocalDateTime nowTime = LocalDateTime.now(ZoneId.of(ZoneEnum.SHANGHAI.getZone()));
            String now = DateTimeFormatter.ofPattern(dateFormat).format(nowTime);

            int activityStatus = Integer.parseInt(activityStatusStr);
            // 未开始
            if (activityStatus == CommonConstant.NOT_STARTED) {
                queryWrapper.gt(Activity::getActivityStartTime, nowTime);
            }

            // 进行中
            if (activityStatus == CommonConstant.IN_PROGRESS) {
                queryWrapper.le(Activity::getActivityStartTime, nowTime)
                        .ge(Activity::getActivityEndTime, nowTime);
            }

            // 已结束
            if (activityStatus == CommonConstant.HAVE_ENDED) {
                queryWrapper.lt(Activity::getActivityEndTime, nowTime);
            }

            // 我创建的
            if (activityStatus == CommonConstant.CREATED_BY_ME) {
                // TODO 使用当前用户id替换
                queryWrapper.eq(Activity::getApplicantId, 1);
            }
        }
        // TODO 分页修改
        /*IPage<Activity> page = this.page(
                new Query<Activity>().getPage(params),
                queryWrapper
        );*/
        IPage<Activity> page = null;

        PageResult pageResult = new PageResult(page);
        List<SimpleActivityVo> simpleActivityVoList = page.getRecords().stream().map(activity -> {
            SimpleActivityVo simpleActivityVo = new SimpleActivityVo();
            BeanUtils.copyProperties(activity, simpleActivityVo);
            return simpleActivityVo;
        }).collect(Collectors.toList());

        pageResult.setList(simpleActivityVoList);
        return pageResult;
    }
}
