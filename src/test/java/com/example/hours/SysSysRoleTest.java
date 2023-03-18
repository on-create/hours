package com.example.hours;

import com.example.hours.service.sys.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysSysRoleTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void selectRoleList() {
        //System.out.println(roleService.selectRoleList(new SysRole(), new PageParams()));
    }
}
