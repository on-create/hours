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

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 活动报名表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("register_activity")
public class RegisterActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动报名表 id
     */
    @TableId
    private Integer id;

    /**
     * 报名用户id
     */
    private Integer userId;

    /**
     * 活动id
     */
    private Integer activityId;

    /**
     * 用户参与状态，0-未参与  1-参与
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JSONField(serialize = false)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JSONField(serialize = false)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
