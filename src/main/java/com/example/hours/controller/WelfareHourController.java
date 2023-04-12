package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.entity.WelfareHour;
import com.example.hours.model.pagination.HourPage;
import com.example.hours.service.WelfareHourService;
import com.example.hours.utils.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

/**
 * 学时控制类
 */
@RestController
@RequestMapping("/welfare_hour")
@Validated
public class WelfareHourController {

    @Autowired
    private WelfareHourService welfareHoursService;

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
     * @return {@link Result<PageResult>}
     */
    @GetMapping("/info_list")
    public Result<PageResult> getHourList(@RequestParam Map<String, Object> params) {
        PageResult page = welfareHoursService.getHourList(params);
        return Result.success(page);
    }

    /**
     * 分页获取学时信息列表
     * @param hourPage 分页请求信息
     * @return 学时信息列表
     */
    @GetMapping("/list")
    public Result<PageResult> getHourList(HourPage hourPage) {
        PageResult result = welfareHoursService.getUserHours(hourPage);
        return Result.success(result);
    }

    /**
     * 删除学时信息
     * @param hourIds 学时id列表
     * @return {@link Result}
     */
    @PostMapping("/delete")
    @PreAuthorize("@permission.hasRole('admin')")
    public Result<?> deleteHour(@RequestBody List<Integer> hourIds) {
        welfareHoursService.deleteHours(hourIds);
        return Result.success();
    }
}
