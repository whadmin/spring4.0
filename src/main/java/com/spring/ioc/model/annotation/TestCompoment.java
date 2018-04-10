package com.spring.ioc.model.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("component")
@Lazy(true)
@Scope("singleton")
@Qualifier("component")
@Primary
public class TestCompoment {
        
    @Autowired
    private ApplicationContext ctx;
    
    public ApplicationContext getCtx() {
        return ctx;
    }
}
