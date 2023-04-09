package com.example.hours.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class AuthUserVO {

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 用户ID列表
     */
    private List<Integer> userIds;
}
