package com.example.hours.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("class_info")
public class ClassInfo extends BaseEntity {

    /**
     * id
     * @mock 1
     */
    private Integer id;

    /**
     * 班级名称
     * @mock 19软件二班
     */
    private String name;

    /**
     * 删除标记
     * @mock 0
     */
    @TableLogic(value = "0", delval = "1")
    @JSONField(serialize = false)
    private Integer delFlag;
}
