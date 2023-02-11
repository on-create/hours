package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.entity.WelfareHour;
import com.example.hours.service.WelfareHoursService;
import com.example.hours.utils.page.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.util.Map;

/**
 * 学时控制类
 */
@RestController
@RequestMapping("/welfare_hour")
@Validated
public class WelfareHourController {

    @Autowired
    private WelfareHoursService welfareHoursService;

    /**
     * 根据学年查找当前用户的学时信息
     * @param year 学年
     * @return {@link Result<>}
     */
    @GetMapping("/info")
    public Result<WelfareHour> getHourInfo(
            @Pattern(regexp = "^20\\d{2}-20\\d{2}$", message = "学年格式不正确")
            @RequestParam("year") String year) {
        WelfareHour welfareHour = welfareHoursService.getHourInfoByYear(year);
        return Result.success(welfareHour);
    }

    /**
     * 查找当前用户的所有学年学时信息
     * @return {@link Result<PageUtils>}
     */
    @GetMapping("/info_list")
    public Result<PageUtils> getHourList(@RequestParam Map<String, Object> params) {
        PageUtils page = welfareHoursService.getHourList(params);
        return Result.success(page);
    }
}
