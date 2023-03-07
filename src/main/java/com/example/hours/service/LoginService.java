package com.example.hours.service;

import com.example.hours.model.vo.LoginUserVo;

import java.util.Map;

public interface LoginService {

    Map<String, Object> login(LoginUserVo user);

    void logout();
}
