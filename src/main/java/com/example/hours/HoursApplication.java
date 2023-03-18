package com.example.hours;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
// TODO 删除dao
@MapperScan(basePackages = {"com.example.hours.dao", "com.example.hours.mapper"})
@SpringBootApplication
public class HoursApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoursApplication.class, args);
    }

}
