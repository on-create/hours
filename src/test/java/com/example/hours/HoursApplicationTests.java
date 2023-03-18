package com.example.hours;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hours.entity.sys.SysRole;
import com.example.hours.service.RegisterActivityService;
import com.example.hours.service.sys.RoleService;
import com.example.hours.utils.CommonUtils;
import com.example.hours.utils.page.PageResult;
import com.example.hours.utils.page.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;

@SpringBootTest
class HoursApplicationTests {

    @Autowired
    private RegisterActivityService registerActivityService;

    @Test
    void testUpdate() {
        registerActivityService.updateSignIn(1);
        registerActivityService.updateSignOut(1);
    }

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

    @Test
    public void testYear() {
        String academicYear = CommonUtils.getAcademicYear();
        System.out.println(academicYear);
        System.out.println(2022 + "-" + (2022 + 1));
    }

    @Test
    public void encoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }

    @Autowired
    private RoleService roleService;

    @Test
    public void testPage() {
        IPage<SysRole> page = roleService.page(
                new Query<SysRole>().getPage(new HashMap<>())
        );
        System.out.println(new PageResult(page));
    }
}
