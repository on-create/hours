package com.example.hours.model.pagination;

import lombok.Data;

@Data
public class BasePage {

    /**
     * 当前页码
     */
    private Integer currPage;

    /**
     * 每页显示条数
     */
    private Integer limit;
}
