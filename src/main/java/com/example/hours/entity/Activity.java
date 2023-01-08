package com.example.hours.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    /**
     * 活动申请人id
     */
    private Integer applicantId;

    /**
     * 活动审批人id
     */
    private Integer approverId;

    /**
     * 活动主题
     */
    private String theme;

    /**
     * 活动简介
     */
    private String content;

    /**
     * 活动图片地址
     */
    private String picture;

    /**
     * 报名人数限制
     */
    private Integer limit;

    /**
     * 活动学时奖励
     */
    private Float reward;

    /**
     * 活动状态
     */
    private Integer status;

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

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
