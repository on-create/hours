package com.example.hours.model.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SimpleActivityVo {

    /**
     * 活动 id
     */
    private Integer id;

    /**
     * 已报名人数
     */
    private Integer number;

    /**
     * 报名人数限制
     */
    private Integer maximum;

    /**
     * 活动图片地址
     */
    private String picture;

    /**
     * 活动主题
     */
    private String theme;

    /**
     * 活动地点
     */
    private String address;

    /**
     * 报名开始时间
     */
    private LocalDateTime applyStartTime;

    /**
     * 报名结束时间
     */
    private LocalDateTime applyEndTime;

    /**
     * 活动开始时间
     */
    private LocalDateTime activityStartTime;

    /**
     * 活动结束时间
     */
    private LocalDateTime activityEndTime;
}
