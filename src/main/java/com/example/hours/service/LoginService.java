package com.example.hours.service;

import com.example.hours.model.vo.LoginUserVO;

import java.util.Map;

public interface LoginService {

    Map<String, Object> login(LoginUserVO user);

    void logout();
}
