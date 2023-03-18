package com.example.hours;

import com.example.hours.service.sys.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysUserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void insert() {
       /* SysUser user = SysUser.builder()
                .password("123456")
                .nickname("昵称")
                .phone("17779192381")
                .sign("个性签名")
                .email("email@qq.com")
                .gender(1)
                .delFlag(1)
                .build();
        userService.save(user);
        System.out.println(user);*/
    }
}
