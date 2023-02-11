package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.Activity;
import com.example.hours.utils.page.PageUtils;
import com.example.hours.vo.ActivityVo;

import java.util.Map;

public interface ActivityService extends IService<Activity> {

    void saveOrUpdateActivity(Activity activity);

    void saveDraft(Activity activity);

    void updateActivity(Activity activity);

    PageUtils getActivityListByStatus(Map<String, Object> params);

    ActivityVo getActivityVo(Integer id);

    void deleteDraft(Integer id);

    void updateDraft(Activity activity);

    PageUtils getMyActivityList(Map<String, Object> params);
}
