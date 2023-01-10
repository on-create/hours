package com.example.hours.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.example.hours.common.validator.group.AddGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    private Integer authority;

    /**
     * 启用状态
     */
    @TableLogic(value = "1", delval = "0")
    private Integer status;

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
