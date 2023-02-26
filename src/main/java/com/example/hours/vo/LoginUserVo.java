package com.example.hours.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserVo {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
