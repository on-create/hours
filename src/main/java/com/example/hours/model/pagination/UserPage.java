package com.example.hours.model.pagination;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * user 分页
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPage extends BasePage {

    /**
     * user ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户状态（0启用 1停用）
     */
    private String status;

    /**
     * 电话号码
     */
    private String phone;
}
