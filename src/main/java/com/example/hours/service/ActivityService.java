package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.Activity;
import com.example.hours.utils.page.PageResult;
import com.example.hours.model.vo.ActivityVo;

import java.util.Map;

public interface ActivityService extends IService<Activity> {

    void saveOrUpdateActivity(Activity activity);

    void saveDraft(Activity activity);

    void updateActivity(Activity activity);

    PageResult getActivityListByStatus(Map<String, Object> params);

    ActivityVo getActivityVo(Integer id);

    void deleteDraft(Integer id);

    void updateDraft(Activity activity);

    PageResult getMyActivityList(Map<String, Object> params);
}
