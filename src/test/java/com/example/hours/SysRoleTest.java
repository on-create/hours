package com.example.hours;

import com.example.hours.entity.Role;
import com.example.hours.service.RoleService;
import com.example.hours.utils.page.PageParams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysRoleTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void selectRoleList() {
        System.out.println(roleService.selectRoleList(new Role(), new PageParams()));
    }
}
