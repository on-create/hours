package com.example.hours.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.hours.common.Result;
import com.example.hours.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 获取 oss签名
     * @return oss签名
     */
    @GetMapping("/getSign")
    public Result<Map<String, Object>> getOssSign() {
        Map<String, Object> ossSign = ossService.getOssSign();
        if (CollectionUtils.isEmpty(ossSign)) {
            return Result.failed("获取签名失败");
        }
        return Result.success(ossSign);
    }
}
