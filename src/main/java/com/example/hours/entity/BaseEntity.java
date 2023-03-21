package com.example.hours.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * entity 基类
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     * @mock 2023-03-17 23:34:05
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     * @mock 2023-03-17 23:34:08
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
