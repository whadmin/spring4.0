package com.spring.aop.xmlSchema;


import com.spring.aop.targetObject.ITargetService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopTest {

    @Test(expected = Exception.class)
    public void testSchemaAdviso() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("aop/spring-schema-advisor-aop.xml");
        ITargetService targetService = ctx.getBean("targetService", ITargetService.class);
        targetService.executeExistParam("param");
        System.out.println("=============================");
        targetService.executeError("param");
        Assert.assertTrue(AopUtils.isAopProxy(targetService));
    }

    @Test(expected = Exception.class)
    public void testSchemaAspect() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("aop/spring-schema-aspect-aop.xml");
        ITargetService targetService = ctx.getBean("targetService", ITargetService.class);
        targetService.executeExistParam("param");
        System.out.println("=============================");
        targetService.executeError("param");
        Assert.assertTrue(AopUtils.isAopProxy(targetService));
    }


}
