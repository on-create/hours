package com.example.hours.entity.sys;

import com.baomidou.mybatisplus.annotation.*;
import com.example.hours.common.validator.group.AddGroup;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_user")
public class SysUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @Null(message = "不能指定id", groups = {AddGroup.class})
    private Integer id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户账号不能为空")
    @Length(max = 30, message = "用户账号长度不能超过30个字符")
    private String username;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空", groups = {AddGroup.class})
    @Length(max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickname;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {AddGroup.class})
    private String password;

    /**
     * 手机号
     */
    @Length(max = 11, message = "手机号码长度不能超过11个字符")
    private String phone;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class})
    @Email(message = "邮箱格式错误", groups = {AddGroup.class})
    @Length(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志
     */
    @TableLogic(value = "0", delval = "1")
    @Null(message = "不能指定状态", groups = {AddGroup.class})
    private Integer delFlag;

    /**
     * 角色对象
     */
    @TableField(exist = false)
    private List<SysRole> sysRoles;

    /**
     * 角色组
     */
    @TableField(exist = false)
    private List<Integer> roleIds;

    /**
     * 角色ID
     */
    @TableField(exist = false)
    private Integer roleId;

    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    public static boolean isAdmin(Integer userId) {
        return userId != null && 1 == userId;
    }

    public SysUser(int id) {
        this.id = id;
    }
}
