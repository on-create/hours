package com.example.hours;

import com.example.hours.dao.RoleMenuDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysSysRoleMenuTest {

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Test
    public void deleteById() {
        roleMenuDao.deleteById(1);
    }
}
