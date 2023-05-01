package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.common.Result;
import com.example.hours.entity.Activity;
import com.example.hours.model.pagination.ActivityPage;
import com.example.hours.model.vo.StatusVO;
import com.example.hours.utils.page.PageResult;
import com.example.hours.model.vo.ActivityVo;

import java.util.List;
import java.util.Map;

public interface ActivityService extends IService<Activity> {

    void saveOrUpdateActivity(Activity activity);

    void saveDraft(Activity activity);

    /**
     * 更新活动信息
     * @param activity 活动信息
     */
    void updateActivity(Activity activity);

    PageResult getActivityListByStatus(Map<String, Object> params);

    ActivityVo getActivityVo(Integer id);

    void deleteDraft(Integer id);

    void updateDraft(Activity activity);

    /**
     * 分页获取活动信息列表（用户报名的活动）
     * @param activityPage 分页搜索条件
     * @return 活动信息列表
     */
    PageResult getMyActivityList(ActivityPage activityPage);

    /**
     * 分页获取活动信息列表
     * @param activityPage 分页搜索条件
     * @return 活动信息列表
     */
    PageResult getActivityList(ActivityPage activityPage);

    /**
     * 根据活动id获取活动详细信息
     * @param activityId 活动id
     * @return 活动信息
     */
    Activity getActivityDetails(Integer activityId);

    /**
     * 添加活动
     * @param activity 活动信息
     */
    void addActivity(Activity activity);

    /**
     * 删除活动
     * @param activityIds 活动ID列表
     */
    void deleteActivities(List<Integer> activityIds);

    /**
     * 更改活动状态
     * @param statusVO 状态信息
     */
    void changeStatus(StatusVO statusVO);

    /**
     * 分页查询活动
     * @param activityPage 查询条件
     * @return 活动列表
     */
    PageResult getActivities(ActivityPage activityPage);
}
