package com.example.hours.model.vo;

import lombok.Data;

/**
 * 用于 post 请求接收单个id
 */
@Data
public class StatusVO {

    /**
     * 主键id
     * @mock 1
     */
    private Integer id;

    /**
     * 状态（0正常 1停用）
     * @mock 0
     */
    private String status;
}
