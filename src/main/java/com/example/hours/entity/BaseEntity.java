package com.example.hours.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * entity 基类
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     * @mock 2023-03-17 23:34:05
     */
    @JSONField(serialize = false)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     * @mock 2023-03-17 23:34:08
     */
    @JSONField(serialize = false)
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
