package com.example.hours.model.vo;

import lombok.Data;

@Data
public class RoleVO {

    /**
     * id
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 启用状态
     */
    private Integer status;
}
