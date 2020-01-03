package com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner;

import com.spring.ioc.bean.assemblyBean.beanObject.annotation.Import.ImportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Import(ImportConfiguration.class)
@Component("testCompoment")
public class TestCompoment {
        
    @Autowired
    private ApplicationContext ctx;
    
    public ApplicationContext getCtx() {
        return ctx;
    }
}
