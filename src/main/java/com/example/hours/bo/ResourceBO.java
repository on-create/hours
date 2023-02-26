package com.example.hours.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceBO {

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

    /**
     * 子资源
     */
    private List<ResourceBO> children;
}
