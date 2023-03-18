package com.example.hours.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("user")
public class User extends BaseEntity {

    /**
     * 用户ID
     * @mock 1
     */
    private Integer id;

    /**
     * 用户名称
     * @mock admin
     */
    private String username;

    /**
     * 用户密码
     * @mock 123456
     */
    private String password;

    /**
     * 邮箱
     * @mock 2640647225@qq.com
     */
    private String email;

    /**
     * 电话号码
     * @mock 17779695831
     */
    private String phone;

    /**
     * 性别（0男 1女 2未知）
     * @mock 0
     */
    private String sex;

    /**
     * 删除标记（0未删除 1已删除）
     * @mock 0
     */
    @TableLogic(value = "0", delval = "1")
    private Integer delFlag;

}
