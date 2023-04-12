package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.ClassInfo;

import java.util.List;

public interface ClassInfoService extends IService<ClassInfo> {

    /**
     * 获取所有班级信息选项
     * @return 班级信息选项列表
     */
    List<ClassInfo> getOptions();
}
