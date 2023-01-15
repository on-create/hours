package com.example.hours;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.example.hours.dao")
@SpringBootApplication
public class HoursApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoursApplication.class, args);
    }

}
