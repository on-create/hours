package com.example.hours.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserHour {

    /**
     * 用户ID
     * @mock 1
     */
    private Integer userId;

    /**
     * 用户姓名
     * @mock 张三
     */
    private String name;

    /**
     * 班级ID
     * @mock 2
     */
    private Integer classId;

    /**
     * 班级名称
     * @mock 19软件二班
     */
    private String className;

    /**
     * 学时ID
     * @mock 1
     */
    private Integer hourId;

    /**
     * 学时数量
     * @mock 5
     */
    private Integer hours;

    /**
     * 学年
     * @mock 2019
     */
    @JsonFormat(pattern = "yyyy", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy")
    private Date year;

    /**
     * 创建时间
     * @mock 2023-03-17 23:34:05
     */
    private LocalDateTime createTime;
}
