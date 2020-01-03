package com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("testAction")
public class TestAction {
    
    @Autowired
    private TestServiceImpl testService;
    
    public void list() {
        //调用业务逻辑层方法
    }
}
