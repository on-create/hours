package com.example.hours.model.pagination;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ActivityPage extends BasePage {

    /**
     * 活动名称
     * @mock 新生见面会
     */
    private String theme;

    /**
     * 活动状态
     * @mock 1 待审批
     */
    private Integer status;

    /**
     * 进度（0 未开始 1 进行中 2已结束）
     */
    private Integer progress;

    /**
     * 我的
     */
    private Boolean mine;

    /**
     * 我创建的（true 是）
     */
    private Boolean createdByMe;
}
