package com.example.hours.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hours.common.validator.group.AddGroup;
import com.example.hours.common.validator.group.TemporaryGroup;
import com.example.hours.common.validator.group.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动 id
     */
    @TableId
    @NotNull(message = "id不能为空", groups = {UpdateGroup.class})
    private Integer id;

    /**
     * 活动申请人id
     */
    @Null(message = "不能指定申请人id", groups = {AddGroup.class, TemporaryGroup.class, UpdateGroup.class})
    private Integer applicantId;

    /**
     * 活动审批人id
     */
    @Null(message = "不能指定审批人id", groups = {AddGroup.class, TemporaryGroup.class, UpdateGroup.class})
    private Integer approverId;

    /**
     * 活动主题
     */
    @Length(max = 30, message = "主题长度不能大于30个字符", groups = {AddGroup.class, TemporaryGroup.class, UpdateGroup.class})
    @NotBlank(message = "主题不能为空", groups = {AddGroup.class})
    private String theme;

    /**
     * 活动地点
     */
    @Length(max = 30, message = "主题长度不能大于30个字符", groups = {AddGroup.class, TemporaryGroup.class, UpdateGroup.class})
    @NotBlank(message = "活动地点不能为空", groups = {AddGroup.class})
    private String address;

    /**
     * 活动简介
     */
    @Length(min = 10, max = 500, message = "简介长度不能小于10或大于500个字符",
            groups = {AddGroup.class, TemporaryGroup.class, UpdateGroup.class})
    @NotBlank(message = "活动简介不能为空", groups = {AddGroup.class})
    private String introduction;

    /**
     * 活动图片地址
     */
    private String picture;

    /**
     * 已报名人数
     */
    @Null(message = "不能设置人数", groups = {UpdateGroup.class})
    private Integer number;

    /**
     * 报名人数限制
     */
    @Min(value = 0, message = "人数限制不能小于0", groups = {AddGroup.class, TemporaryGroup.class, UpdateGroup.class})
    private Integer maximum;

    /**
     * 活动学时奖励
     */
    @Max(value = 9, message = "单次奖励学时不能超过9个学时", groups = {AddGroup.class, TemporaryGroup.class})
    @NotNull(message = "学时奖励不能为空", groups = {AddGroup.class})
    private Float reward;

    /**
     * 签到方式
     */
    @Min(value = 0, message = "不能为负数", groups = {AddGroup.class, TemporaryGroup.class, UpdateGroup.class})
    private Integer signIn;

    /**
     * 签退方式
     */
    @Min(value = 0, message = "不能为负数", groups = {AddGroup.class, TemporaryGroup.class, UpdateGroup.class})
    private Integer signOut;

    /**
     * 活动状态
     */
    @Null(message = "不能设置状态", groups = {AddGroup.class, TemporaryGroup.class, UpdateGroup.class})
    private Integer status;

    /**
     * 报名开始时间
     */
    @NotNull(message = "报名开始时间不能为空", groups = {AddGroup.class})
    private LocalDateTime applyStartTime;

    /**
     * 报名结束时间
     */
    @NotNull(message = "报名结束时间不能为空", groups = {AddGroup.class})
    private LocalDateTime applyEndTime;

    /**
     * 活动开始时间
     */
    @NotNull(message = "活动开始时间不能为空", groups = {AddGroup.class})
    private LocalDateTime activityStartTime;

    /**
     * 活动结束时间
     */
    @NotNull(message = "活动结束时间不能为空", groups = {AddGroup.class})
    private LocalDateTime activityEndTime;

    /**
     * 创建时间
     */
    @JSONField(serialize = false)
    @TableField(fill = FieldFill.INSERT)
    @Null(message = "不能指定创建时间", groups = {AddGroup.class, TemporaryGroup.class, UpdateGroup.class})
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JSONField(serialize = false)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Null(message = "不能指定更新时间", groups = {AddGroup.class, TemporaryGroup.class, UpdateGroup.class})
    private LocalDateTime updateTime;
}
