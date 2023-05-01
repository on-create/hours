package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.common.Result;
import com.example.hours.common.constant.CommonConstant;
import com.example.hours.common.constant.EntityConstant;
import com.example.hours.common.enums.ZoneEnum;
import com.example.hours.dao.ActivityDao;
import com.example.hours.entity.Activity;
import com.example.hours.exception.DraftException;
import com.example.hours.exception.HourException;
import com.example.hours.exception.TimeValidException;
import com.example.hours.mapper.UserRoleMapper;
import com.example.hours.model.pagination.ActivityPage;
import com.example.hours.model.vo.StatusVO;
import com.example.hours.service.ActivityService;
import com.example.hours.service.RegisterActivityService;
import com.example.hours.utils.CommonUtils;
import com.example.hours.utils.SecurityUtils;
import com.example.hours.utils.page.PageResult;
import com.example.hours.model.vo.ActivityVo;
import com.example.hours.model.vo.SimpleActivityVo;
import com.example.hours.utils.page.PageUtils;
import com.example.hours.utils.page.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("activityService")
public class ActivityServiceImpl extends ServiceImpl<ActivityDao, Activity> implements ActivityService {

    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @Autowired
    private RegisterActivityService registerActivityService;

    @Autowired
    private UserRoleMapper userRoleMapper;

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
                boolean flag = (status != null && applicantId != null && status == EntityConstant.ACTIVITY_DRAFT);
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
        activity.setStatus(EntityConstant.ACTIVITY_DRAFT);
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
            activity.setMaximum(EntityConstant.ACTIVITY_NUMBER_UNLIMITED);
        }
    }

    /**
     * 更新活动信息
     * @param activity 活动信息
     */
    @Override
    public void updateActivity(Activity activity) {
        // 获取活动状态信息
        Integer status = this.baseMapper.selectOne(
                new LambdaQueryWrapper<Activity>()
                        .eq(Activity::getId, activity.getId())
                        .select(Activity::getStatus)
        ).getStatus();

        // 待审批状态修改为已审批状态，记录审批者ID
        if (EntityConstant.ACTIVITY_PENDING_APPROVAL == status && EntityConstant.ACTIVITY_APPROVE == activity.getStatus()) {
            // 设置审批者ID
            activity.setApproverId(SecurityUtils.getUserId());
        }
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
            if (status == EntityConstant.ACTIVITY_DRAFT) {
                queryWrapper.eq(Activity::getStatus, EntityConstant.ACTIVITY_DRAFT);
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
                        .eq("status", EntityConstant.ACTIVITY_DRAFT)
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

    /**
     * 分页获取活动信息列表
     * @param activityPage 分页搜索条件
     * @return 活动信息列表
     */
    @Override
    public PageResult getActivityList(ActivityPage activityPage) {
        IPage<Activity> page = this.baseMapper.selectPage(
                PageUtils.initPage(activityPage),
                new LambdaQueryWrapper<Activity>()
                        .like(StringUtils.isNotBlank(activityPage.getTheme()), Activity::getTheme, activityPage.getTheme())
                        .eq(Objects.nonNull(activityPage.getStatus()), Activity::getStatus, activityPage.getStatus())
                        .ne(Activity::getStatus, EntityConstant.ACTIVITY_DRAFT)
                        .select(Activity::getId, Activity::getTheme, Activity::getAddress,
                                Activity::getStatus, Activity::getSignIn, Activity::getActivityStartTime)
        );
        return new PageResult(page);
    }

    /**
     * 根据活动id获取活动详细信息
     * @param activityId 活动id
     * @return 活动信息
     */
    @Override
    public Activity getActivityDetails(Integer activityId) {
        return this.baseMapper.selectOne(
                new LambdaQueryWrapper<Activity>()
                        .eq(Activity::getId, activityId)
                        .select(Activity::getId, Activity::getTheme, Activity::getAddress,
                                Activity::getIntroduction, Activity::getPicture, Activity::getMaximum,
                                Activity::getReward, Activity::getSignIn, Activity::getSignOut,
                                Activity::getStatus, Activity::getApplyStartTime, Activity::getApplyEndTime,
                                Activity::getActivityStartTime, Activity::getActivityEndTime)
        );
    }

    /**
     * 添加活动
     * @param activity 活动信息
     */
    @Override
    public void addActivity(Activity activity) {
        // 校验时间
        isValid(activity);
        // TODO 设置签到
        Integer userId = SecurityUtils.getUserId();
        Integer roleId = userRoleMapper.selectRoleId(userId);
        // 设置申请人id
        activity.setApplicantId(userId);
        // 设置活动报名人数
        activity.setNumber(EntityConstant.ACTIVITY_NUMBER_ZERO);
        if (EntityConstant.ROLE_APPROVER_ID.equals(roleId) || EntityConstant.ROLE_ADMIN_ID.equals(roleId)) {
            // 审批者 创建活动
            activity.setApproverId(userId);
            activity.setStatus(EntityConstant.ACTIVITY_APPROVE);
        }

        if (EntityConstant.ROLE_APPLICANT_ID.equals(roleId)) {
            // 申请者
            activity.setStatus(EntityConstant.ACTIVITY_PENDING_APPROVAL);
            // TODO 设置审批人ID
        }

        this.save(activity);
    }

    /**
     * 删除活动
     * @param activityIds 活动ID列表
     */
    @Override
    public void deleteActivities(List<Integer> activityIds) {
        for (Integer activityId : activityIds) {
            // 根据活动id获取活动报名开始/结束时间，活动开始/结束时间，审批状态
            Activity activity = this.baseMapper.selectOne(
                    new LambdaQueryWrapper<Activity>()
                            .eq(Activity::getId, activityId)
                            .select(Activity::getApplyStartTime, Activity::getActivityStartTime, Activity::getStatus,
                                    Activity::getApplyEndTime, Activity::getActivityEndTime)
            );

            // 已审批状态
            if (activity.getStatus().equals(EntityConstant.ACTIVITY_APPROVE)) {
                if (CommonUtils.getNowTime().isAfter(activity.getApplyStartTime()) &&
                        CommonUtils.getNowTime().isBefore(activity.getApplyEndTime())) {
                    throw new HourException("活动报名期间，禁止删除");
                }

                if (CommonUtils.getNowTime().isAfter(activity.getActivityStartTime()) &&
                        CommonUtils.getNowTime().isBefore(activity.getActivityEndTime())) {
                    throw new HourException("活动进行期间，禁止删除");
                }
            }
        }
        // 批量删除
        this.baseMapper.deleteBatchIds(activityIds);
    }

    /**
     * 更改活动状态
     * @param statusVO 状态信息
     */
    @Override
    public void changeStatus(StatusVO statusVO) {
        Activity activity = Activity.builder()
                .id(statusVO.getId())
                .status(statusVO.getMultiStatus())
                .build();

        // 获取审批者ID
        Integer userId = SecurityUtils.getUserId();
        activity.setApproverId(userId);
        this.baseMapper.updateById(activity);
    }

    /**
     * 分页查询活动（小程序）
     * @param activityPage 查询条件
     * @return 活动列表
     */
    @Override
    public PageResult getActivities(ActivityPage activityPage) {
        LambdaQueryWrapper<Activity> queryWrapper = getQueryWrapper(activityPage);
        IPage<Activity> page = this.baseMapper.selectPage(
                PageUtils.initPage(activityPage),
                queryWrapper
                        .like(StringUtils.isNotBlank(activityPage.getTheme()), Activity::getTheme, activityPage.getTheme())
                        .select(Activity::getId, Activity::getTheme, Activity::getAddress, Activity::getPicture,
                                Activity::getActivityStartTime, Activity::getActivityEndTime,
                                Activity::getApplyStartTime, Activity::getApplyEndTime)
        );
        return new PageResult(page);
    }

    /**
     * 分页获取活动信息列表（用户报名的活动）
     * @param activityPage 分页搜索条件
     * @return 活动信息列表
     */
    @Override
    public PageResult getMyActivityList(ActivityPage activityPage) {
        // 当前用户报名的活动id列表
        List<Integer> activityIds = registerActivityService.getActivityIds();
        LambdaQueryWrapper<Activity> queryWrapper = getQueryWrapper(activityPage);
        IPage<Activity> page = this.baseMapper.selectPage(
                PageUtils.initPage(activityPage),
                queryWrapper
                        .like(StringUtils.isNotBlank(activityPage.getTheme()), Activity::getTheme, activityPage.getTheme())
                        .in(Activity::getId, activityIds)
                        .select(Activity::getId, Activity::getTheme, Activity::getAddress, Activity::getPicture,
                                Activity::getActivityStartTime, Activity::getActivityEndTime,
                                Activity::getApplyStartTime, Activity::getApplyEndTime)
        );
        return new PageResult(page);
    }

    /**
     * 根据查询条件获取公共 LambdaQueryWrapper
     * @param activityPage 查询条件
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<Activity> getQueryWrapper(ActivityPage activityPage) {
        // 只查询已审批活动
        LambdaQueryWrapper<Activity> queryWrapper = new LambdaQueryWrapper<Activity>()
                .eq(Activity::getStatus, EntityConstant.ACTIVITY_APPROVE);

        // 活动进度
        if (Objects.nonNull(activityPage.getProgress())) {
            Integer progress = activityPage.getProgress();
            switch (progress) {
                case EntityConstant.ACTIVITY_NOT_STARTED:
                    // 未开始
                    queryWrapper.gt(Activity::getActivityStartTime, CommonUtils.getNowTime());
                    break;
                case EntityConstant.ACTIVITY_IN_PROGRESS:
                    // 进行中
                    queryWrapper.le(Activity::getActivityStartTime, CommonUtils.getNowTime())
                            .ge(Activity::getActivityEndTime, CommonUtils.getNowTime());
                    break;
                case EntityConstant.ACTIVITY_HAVE_ENDED:
                    // 已结束
                    queryWrapper.lt(Activity::getActivityEndTime, CommonUtils.getNowTime());
                    break;
            }
        }

        // 我创建的
        if (Objects.nonNull(activityPage.getCreatedByMe()) && activityPage.getCreatedByMe()) {
            queryWrapper.eq(Activity::getApplicantId, SecurityUtils.getUserId());
        }

        // 排序
        queryWrapper.orderByDesc(Activity::getActivityStartTime);
        return queryWrapper;
    }
}
