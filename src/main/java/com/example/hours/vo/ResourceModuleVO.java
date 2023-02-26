package com.example.hours.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceModuleVO {

    /**
     * id
     */
    private Integer id;

    /**
     * 模块名称
     */
    private String name;
}
