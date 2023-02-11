package com.example.hours.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class RegisterActivityVo {

    /**
     * 活动 id
     */
    @NotNull(message = "活动id不能为空")
    private Integer id;

    /**
     * 已报名人数
     */
    @NotNull(message = "已报名人数不能为空")
    private Integer number;

    /**
     * 报名人数限制
     */
    @NotNull(message = "报名人数限制不能为空")
    private Integer maximum;

    /**
     * 报名开始时间
     */
    @NotNull(message = "报名开始时间不能为空")
    private LocalDateTime applyStartTime;

    /**
     * 报名结束时间
     */
    @NotNull(message = "报名结束时间不能为空")
    private LocalDateTime applyEndTime;
}
