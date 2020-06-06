package com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component("testCompoment")
public class TestCompoment {
        
    @Autowired
    private ApplicationContext ctx;
    
    public ApplicationContext getCtx() {
        return ctx;
    }
}
