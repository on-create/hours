package com.example.hours;

import com.example.hours.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 测试反序列化问题
     */
    @Test
    public void redisTest() {
        /*Teacher teacher = new Teacher();
        teacher.name = "教师";
        List<Student> list = new ArrayList<>();
        list.add(new Student("张三"));
        list.add(new Student("李四"));
        list.add(new Student("王五"));
        teacher.students = list;
        redisUtils.set("teacherDemo", teacher);
        Teacher teacherDemo = (Teacher) redisUtils.get("teacherDemo");
        System.out.println(teacherDemo);*/
    }

    static class Teacher {
        String name;
        List<Student> students;

        @Override
        public String toString() {
            return "Teacher{" +
                    "name='" + name + '\'' +
                    ", students=" + students +
                    '}';
        }
    }

    static class Student {
        String name;
        Student(String name) {
            this.name = name;
        }
        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
