package com.example.hours;

import com.example.hours.utils.CommonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HoursApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testCommonUtils() {
        Student student = new Student(1, null);
        System.out.println(CommonUtils.checkObjAllFieldsIsNull(student));
    }

    static class Student {
        int id;
        String name;

        Student(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
