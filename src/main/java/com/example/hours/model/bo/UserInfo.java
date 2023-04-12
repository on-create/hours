package com.example.hours.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {

    /**
     * 用户ID
     * @mock 1
     */
    private Integer userId;

    /**
     * 用户名称
     * @mock admin
     */
    private String username;

    /**
     * 姓名
     * @mock 张三
     */
    private String name;

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
     * 角色ID
     * @mock 1
     */
    private Integer roleId;
}
