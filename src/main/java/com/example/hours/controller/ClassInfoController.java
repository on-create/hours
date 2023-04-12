package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.entity.ClassInfo;
import com.example.hours.service.ClassInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 班级信息控制类
 */
@RestController
@RequestMapping("/classInfo")
public class ClassInfoController {

    @Autowired
    private ClassInfoService classInfoService;

    /**
     * 获取所有班级信息选项
     * @return 班级信息选项列表
     */
    @RequestMapping("/options")
    public Result<List<ClassInfo>> getAllClassInfoOptions() {
        List<ClassInfo> options = classInfoService.getOptions();
        return Result.success(options);
    }
}
