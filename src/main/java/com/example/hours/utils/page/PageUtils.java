package com.example.hours.utils.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hours.model.pagination.BasePage;

import java.util.List;
import java.util.Objects;

/**
 * 分页工具类
 */
public class PageUtils {

    public static <T> IPage<T> initPage(BasePage basePage) {
        // 分页参数
        long currPage = 1L;
        long limit = 10L;

        if (Objects.nonNull(basePage.getCurrPage())) {
            currPage = basePage.getCurrPage();
        }

        if (Objects.nonNull(basePage.getLimit())) {
            limit = basePage.getLimit();
        }
        return new Page<>(currPage, limit);
    }

    /**
     * 初始化分页
     * @param params 分页参数信息
     * @return Page
     */
	public static IPage<?> initPage(PageParams params) {
	    // 分页参数
	    long currPage = 1L;
	    long limit = 10L;

        if (Objects.nonNull(params.getCurrPage())) {
            currPage = params.getCurrPage();
        }

        if (Objects.nonNull(params.getLimit())) {
            limit = params.getLimit();
        }
        return new Page<>(currPage, limit);
    }

    /**
     * 获取分页查询结果
     * @param page 分页参数
     * @return 分页查询结果
     */
    public static PageResult selectResult(IPage<?> page, List<?> records) {
        PageResult pageResult = new PageResult(page);
        pageResult.setList(records);
        return pageResult;
    }
}
