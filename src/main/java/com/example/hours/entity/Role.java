package com.example.hours.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("role")
public class Role extends BaseEntity {

    /**
     * 角色ID
     * @mock 1
     */
    private Integer id;

    /**
     * 角色名称
     * @mock 管理员
     */
    private String name;

    /**
     * 角色权限字符串
     * @mock admin
     */
    @TableField("`key`")
    private String key;

    /**
     * 显示顺序
     * @mock 1
     */
    private Integer sort;

    /**
     * 角色状态（0正常 1停用）
     * @mock 0
     */
    private String status;

    /**
     * 删除标记（0未删除 1已删除）
     * @mock 0
     */
    @TableLogic(value = "0", delval = "1")
    private Integer delFlag;
}
