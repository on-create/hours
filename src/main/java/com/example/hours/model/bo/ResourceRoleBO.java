package com.example.hours.model.bo;

import lombok.Data;

import java.util.List;

@Data
public class ResourceRoleBO {

    /**
     * id
     */
    private Integer id;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 是否允许匿名访问（0否 1是）
     */
    private Integer isAnonymous;

    /**
     * 角色列表
     */
    private List<String> roleList;
}
