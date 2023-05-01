package com.example.hours.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface OssService {

    /**
     * 获取 oss签名
     * @return oss签名
     */
    Map<String, Object> getOssSign();
}
