package com.example.hours.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("welfare_hour")
public class WelfareHour extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     * @mock 1
     */
    @TableId
    private Integer id;

    /**
     * 用户 id
     * @mock 1
     */
    private Integer userId;

    /**
     * 学时
     * @mock 1
     */
    private Integer hours;

    /**
     * 学年
     * @mock 2019
     */
    private Date year;
}
