package com.example.hours;

import com.example.hours.entity.User;
import com.example.hours.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void insert() {
        User user = User.builder()
                .password("123456")
                .nickname("昵称")
                .mobile("17779192381")
                .sign("个性签名")
                .email("email@qq.com")
                .gender(1)
                .status(1)
                .build();
        userService.save(user);
        System.out.println(user);
    }
}
