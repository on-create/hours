package com.example.hours.utils.page;

import lombok.Data;

@Data
public class PageParams {

    /**
     * 当前页码
     */
    private Long currPage;

    /**
     * 每页显示记录数
     */
    private Long limit;

    /**
     * 排序字段
     */
    private String orderField;

    /**
     * 排序方式
     */
    private String order;
}
