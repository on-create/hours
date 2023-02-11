package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.common.validator.group.AddGroup;
import com.example.hours.common.validator.group.TemporaryGroup;
import com.example.hours.common.validator.group.UpdateGroup;
import com.example.hours.entity.Activity;
import com.example.hours.service.ActivityService;
import com.example.hours.utils.page.PageUtils;
import com.example.hours.vo.ActivityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@RequestMapping("/activity")
@Validated
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * 活动列表（根据 status 区分草稿、待审批，审批通过）
     * @return {@link Result<PageUtils>}
     */
    @GetMapping("/list")
    public Result<PageUtils> getActivityList(@RequestParam Map<String, Object> params) {
        PageUtils page = activityService.getActivityListByStatus(params);
        return Result.success(page);
    }

    /**
     * 我的活动列表
     * @return {@link Result<PageUtils>}
     */
    @GetMapping("/myList")
    public Result<PageUtils> getMyActivityList(@RequestParam Map<String, Object> params) {
        PageUtils page = activityService.getMyActivityList(params);
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
    public Result<Object> updateActivity(@Validated(UpdateGroup.class) @RequestBody Activity activity) {
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
}
