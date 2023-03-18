package com.example.hours.utils.page;

import lombok.Data;

@Data
public class PageParams {

    /**
     * 当前页码
     */
    private Integer currPage;

    /**
     * 每页显示记录数
     */
    private Integer limit;

    /**
     * 排序字段
     */
    private String orderField;

    /**
     * 排序方式
     */
    private String order;

    public PageParams(Integer currPage, Integer limit) {
        this.currPage = currPage;
        this.limit = limit;
    }
}
