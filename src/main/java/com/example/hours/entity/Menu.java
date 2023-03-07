package com.example.hours.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单权限表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_menu")
public class Menu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @TableId
    private Integer id;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Length(min = 0, max = 50, message = "菜单名称长度不能超过50个字符")
    private String name;

    /**
     * 父菜单id
     */
    private Integer parentId;

    /**
     * 父菜单名称
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    @Length(min = 0, max = 200, message = "路由地址不能超过200个字符")
    private String path;

    /**
     * 组件路径
     */
    @Length(min = 0, max = 255, message = "组件路径不能超过255个字符")
    private String component;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @NotBlank(message = "菜单类型不能为空")
    private String type;

    /**
     * 显示状态（0显示  1隐藏）
     */
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;

    /**
     * 权限字符串
     */
    @Length(min = 0, max = 100, message = "权限标识长度不能超过100个字符")
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<Menu> children;
}
