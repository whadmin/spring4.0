package com.spring.ioc.model.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TestAction {
    
    @Autowired
    private TestServiceImpl testService;
    
    public void list() {
        //调用业务逻辑层方法
    }
}
