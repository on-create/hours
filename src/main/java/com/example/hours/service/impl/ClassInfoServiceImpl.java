package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.entity.ClassInfo;
import com.example.hours.mapper.ClassInfoMapper;
import com.example.hours.service.ClassInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("classInfoService")
public class ClassInfoServiceImpl extends ServiceImpl<ClassInfoMapper, ClassInfo> implements ClassInfoService {

    /**
     * 获取所有班级信息选项
     * @return 班级信息选项列表
     */
    @Override
    public List<ClassInfo> getOptions() {
        return this.baseMapper.selectList(
                new LambdaQueryWrapper<ClassInfo>()
                        .select(ClassInfo::getId, ClassInfo::getName)
        );
    }
}
