package com.example.hours.model;

import com.example.hours.utils.page.PageParams;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 携带分页信息的请求信息实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBody<T> {

    /**
     * 实际的请求对象信息
     */
    private T data;

    /**
     * 分页信息
     */
    private PageParams params;
}
