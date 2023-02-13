package com.example.hours.strategy.context;

import com.example.hours.strategy.SignStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 上下文
 */
@Service
public class SignContext {

    @Autowired
    private Map<String, SignStrategy> signStrategyMap;

    public void sign() {

    }
}
