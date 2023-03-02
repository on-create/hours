package com.example.hours.vo;

import com.example.hours.common.validator.group.AddGroup;
import com.example.hours.common.validator.group.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceModuleVO {

    /**
     * id
     */
    @NotNull(message = "模块id不能为空", groups = {UpdateGroup.class})
    private Integer id;

    /**
     * 模块名称
     */
    @Length(min = 1, max = 50, message = "模块名称长度范围 1~50", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "模块名称不能为空", groups = {AddGroup.class})
    private String name;

    /**
     * 启用状态（0启用 1禁用）
     */
    @Max(value = 1, message = "不能超过最大值1", groups = {UpdateGroup.class})
    @Min(value = 0, message = "不能低于最小值0", groups = {UpdateGroup.class})
    private Integer status;
}
