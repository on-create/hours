package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.common.validator.group.AddGroup;
import com.example.hours.common.validator.group.TemporaryGroup;
import com.example.hours.common.validator.group.UpdateGroup;
import com.example.hours.entity.Activity;
import com.example.hours.model.pagination.ActivityPage;
import com.example.hours.model.vo.StatusVO;
import com.example.hours.service.ActivityService;
import com.example.hours.utils.page.PageResult;
import com.example.hours.model.vo.ActivityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 活动控制类
 */
@RestController
@RequestMapping("/activity")
@Validated
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * 活动列表（根据 status 区分草稿、待审批，审批通过）
     * @return {@link Result<PageResult>}
     */
    @GetMapping("/list_status")
    public Result<PageResult> getActivityListStatus(@RequestParam Map<String, Object> params) {
        PageResult page = activityService.getActivityListByStatus(params);
        return Result.success(page);
    }

    /**
     * 我的活动列表
     * @param activityPage 查询条件
     * @return {@link Result<PageResult>}
     */
    @GetMapping("/myList")
    public Result<PageResult> getMyActivityList(ActivityPage activityPage) {
        PageResult page = activityService.getMyActivityList(activityPage);
        return Result.success(page);
    }

    /**
     * 分页获取活动列表（审批已通过）
     * @param activityPage 查询条件
     * @return 活动列表
     */
    @GetMapping("/list")
    public Result<PageResult> getActivities(ActivityPage activityPage) {
        PageResult page = activityService.getActivities(activityPage);
        return Result.success(page);
    }

    /**
     * 获取活动的详细信息
     * @param id 活动 id
     * @return {@link Result<ActivityVo>}
     */
    @GetMapping("/info")
    public Result<ActivityVo> getActivityInfo(
            @NotNull(message = "id 不能为空")
            @Min(value = 1, message = "id 必须是正数")
            @RequestParam("id") Integer id) {
        ActivityVo activityVo = activityService.getActivityVo(id);
        return Result.success(activityVo);
    }

    /**
     * 申请活动
     * @param activity 活动
     * @return {@link Result<>}
     */
    @PostMapping("/apply")
    public Result<Object> applyActivity(@Validated(AddGroup.class) @RequestBody Activity activity) {
        activityService.saveOrUpdateActivity(activity);
        return Result.success();
    }

    /**
     * 保存活动草稿
     * @param activity 活动
     * @return {@link Result<>}
     */
    @PostMapping("/save_draft")
    public Result<Object> saveDraft(@Validated(TemporaryGroup.class) @RequestBody Activity activity) {
        activityService.saveDraft(activity);
        return Result.success();
    }

    /**
     * 更新草稿信息
     * @param activity 活动
     * @return {@link Result<>}
     */
    @PostMapping("/update_draft")
    public Result<Object> updateDraft(@Validated(TemporaryGroup.class) @RequestBody Activity activity) {
        activityService.updateDraft(activity);
        return Result.success();
    }

    /**
     * 更新活动信息
     * @param activity 活动
     * @return {@link Result<>}
     */
    @PostMapping("/update_activity")
    public Result<Object> updateActivity1(@Validated(UpdateGroup.class) @RequestBody Activity activity) {
        activityService.updateActivity(activity);
        return Result.success();
    }

    /**
     * 删除草稿
     * @param id 活动 id
     * @return {@link Result<>}
     */
    @GetMapping("/deleteDraft")
    public Result<Object> deleteDraft(
            @NotNull(message = "id 不能为空")
            @Min(value = 1, message = "id 必须是正数")
            @RequestParam("id") Integer id) {
        activityService.deleteDraft(id);
        return Result.success();
    }

    /**
     * 分页获取活动信息列表
     * @param activityPage 分页搜索条件
     * @return 活动信息列表
     */
    @GetMapping("/pc/list")
    public Result<PageResult> getActivityList(ActivityPage activityPage) {
        PageResult result = activityService.getActivityList(activityPage);
        return Result.success(result);
    }

    /**
     * 根据活动id获取活动详细信息
     * @param activityId 活动id
     * @return 活动信息
     */
    @GetMapping("/details")
    public Result<Activity> getActivityDetails(@RequestParam("activityId") Integer activityId) {
        Activity activity = activityService.getActivityDetails(activityId);
        return Result.success(activity);
    }

    /**
     * 更新活动信息
     * @param activity 最新的活动信息
     * @return {@link Result}
     */
    @PostMapping("/update")
    //@PreAuthorize("@permission.hasRole('admin')") TODO 审批者
    public Result<?> updateActivity(@RequestBody Activity activity) {
        activityService.updateActivity(activity);
        return Result.success();
    }

    /**
     * 添加活动
     * @param activity 活动信息
     * @return {@link Result}
     */
    @PostMapping("/add")
    public Result<?> addActivity(@RequestBody Activity activity) {
        activityService.addActivity(activity);
        return Result.success();
    }

    /**
     * 删除活动
     * @param activityIds 活动ID列表
     * @return {@link Result}
     */
    @PostMapping("/delete")
    public Result<?> deleteActivities(@RequestBody List<Integer> activityIds) {
        activityService.deleteActivities(activityIds);
        return Result.success();
    }

    /**
     * 更改活动状态
     * @param statusVO 状态信息
     * @return {@link Result}
     */
    @PostMapping("/changeStatus")
    public Result<?> changeActivityStatus(@RequestBody StatusVO statusVO) {
        activityService.changeStatus(statusVO);
        return Result.success();
    }
}
