package com.example.hours.model.pagination;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HourPage extends BasePage {

    /**
     * 用户姓名
     * @mock 张三
     */
    private String name;

    /**
     * 班级id
     * @mock 2
     */
    private Integer classId;

    /**
     * 学年
     * @mock 2019
     */
    @DateTimeFormat(pattern = "yyyy")
    private Date year;
}
