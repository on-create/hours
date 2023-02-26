package com.example.hours.vo;

import lombok.Data;

@Data
public class ResourceVO {

    /**
     * id
     */
    private Integer id;

    /**
     * 资源名
     */
    private String name;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 是否禁用（0启用 1禁用）
     */
    private Integer status;

    /**
     * 是否允许匿名访问（0否 1是）
     */
    private Integer isAnonymous;
}
