package com.example.hours.model.vo;

import lombok.Data;

@Data
public class UserPwdVO {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 新密码
     */
    private String password;
}
