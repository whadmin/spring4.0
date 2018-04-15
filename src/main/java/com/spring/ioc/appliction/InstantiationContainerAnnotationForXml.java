package com.spring.ioc.appliction;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.model.annotation.TestAction;
import com.spring.ioc.model.annotation.TestCache;
import com.spring.ioc.model.annotation.TestCompoment;
import com.spring.ioc.model.annotation.TestDaoImpl;
import com.spring.ioc.model.annotation.TestServiceImpl;

public class InstantiationContainerAnnotationForXml {

    private static String             configLocation = "classpath:ioc/instantiationBeanAnnotation.xml";
    private static ApplicationContext ctx            = new ClassPathXmlApplicationContext(configLocation);

    @Test
    public void testComponent() {
        TestCompoment component = ctx.getBean("component", TestCompoment.class);
        Assert.assertNotNull(component.getCtx());

        TestDaoImpl dao = ctx.getBean("testDao", TestDaoImpl.class);
        Assert.assertNotNull(dao);

        TestServiceImpl service = ctx.getBean("testService", TestServiceImpl.class);
        Assert.assertNotNull(service.getDao());

        TestAction action = ctx.getBean("testAction", TestAction.class);
        Assert.assertNotNull(action);

        TestCache cache = ctx.getBean("cache", TestCache.class);
        Assert.assertNotNull(cache);
        
//        TestBean14 testBean14 = ctx.getBean("testBean14", TestBean14.class);
//        Assert.assertNotNull(testBean14);
    }

}
