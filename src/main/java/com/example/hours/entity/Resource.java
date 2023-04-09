package com.example.hours.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hours.common.validator.group.AddGroup;
import com.example.hours.common.validator.group.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_resource")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "资源id不能为空", groups = {UpdateGroup.class})
    private Integer id;

    /**
     * 资源名
     */
    @Length(min = 1, max = 50, message = "资源名称长度范围 1~50", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "资源名称不能为空", groups = {AddGroup.class})
    private String name;

    /**
     * 请求路径
     */
    @Length(min = 1, max = 50, message = "请求路径长度范围 1~50", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "请求路径不能为空", groups = {AddGroup.class})
    private String url;

    /**
     * 请求方式
     */
    @Length(min = 1, max = 10, message = "请求方式长度范围 1~10", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "请求方式不能为空", groups = {AddGroup.class})
    private String requestMethod;

    /**
     * 父id
     */
    @Min(value = 1, message = "归属模块不存在", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "归属模块不能为空", groups = {AddGroup.class})
    private Integer parentId;

    /**
     * 是否匿名访问
     */
    @Max(value = 1, message = "不能超过最大值1", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "不能低于最小值0", groups = {AddGroup.class, UpdateGroup.class})
    private Integer isAnonymous;

    /**
     * 是否启用（0启用 1停用）
     */
    @Max(value = 1, message = "不能超过最大值1", groups = {UpdateGroup.class})
    @Min(value = 0, message = "不能低于最小值0", groups = {UpdateGroup.class})
    private String status;

    /**
     * 是否删除（0未删除 1已删除）
     */
    @TableLogic(value = "0", delval = "1")
    @Null(message = "不能修改删除标志", groups = {UpdateGroup.class})
    private Integer delFlag;

    /**
     * 创建时间
     */
    @JSONField(serialize = false)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JSONField(serialize = false)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
