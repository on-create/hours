package com.example.hours.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.example.hours.common.validator.group.AddGroup;
import com.example.hours.common.validator.group.TemporaryGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @Null(message = "不能指定id", groups = {AddGroup.class})
    private Integer id;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空", groups = {AddGroup.class})
    private String nickname;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {AddGroup.class})
    private String password;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 个性签名
     */
    private String sign;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class})
    @Email(message = "邮箱格式错误", groups = {AddGroup.class})
    private String email;

    /**
     * 头像
     */
    private String header;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 活动申请权限
     */
    @Null(message = "不能指定申请权限", groups = {AddGroup.class})
    private Integer authority;

    /**
     * 启用状态
     */
    @TableLogic(value = "1", delval = "0")
    @Null(message = "不能指定状态", groups = {AddGroup.class})
    private Integer status;

    /**
     * 创建时间
     */
    @JSONField(serialize = false)
    @TableField(fill = FieldFill.INSERT)
    @Null(message = "不能指定创建时间", groups = {AddGroup.class})
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JSONField(serialize = false)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Null(message = "不能指定更新时间", groups = {AddGroup.class})
    private LocalDateTime updateTime;
}
