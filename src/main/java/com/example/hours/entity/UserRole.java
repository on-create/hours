package com.example.hours.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("user_role")
public class UserRole {

    /**
     * 用户ID
     * @mock 1
     */
    private Integer userId;

    /**
     * 角色ID
     * @mock 1
     */
    private Integer roleId;
}
