package com.example.hours;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hours.dao.RoleDao;
import com.example.hours.entity.Role;
import com.example.hours.model.RoleUser;
import com.example.hours.service.RegisterActivityService;
import com.example.hours.service.RoleService;
import com.example.hours.utils.CommonUtils;
import com.example.hours.utils.page.PageParams;
import com.example.hours.utils.page.PageResult;
import com.example.hours.utils.page.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.List;

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
        System.out.println(encoder.encode("12345678"));
    }

    @Autowired
    private RoleService roleService;

    @Test
    public void testPage() {
        IPage<Role> page = roleService.page(
                new Query<Role>().getPage(new HashMap<>())
        );
        System.out.println(new PageResult(page));
    }
}
