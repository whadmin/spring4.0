package com.spring.ioc.bean;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.ListBean;
import junit.framework.Assert;

public class AutowireInjectBeanForXml {
	
    @Test
    public void testAutowireByName() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInjectAutowire.xml");
        HelloApi helloApi = context.getBean("bean_byName", HelloApi.class);
        helloApi.sayHello();
    }

    @Test
    public void testAutowireByType1() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInjectAutowire.xml");
        HelloApi helloApi = context.getBean("bean_byType1", HelloApi.class);
        helloApi.sayHello();
    }

    @Test
    public void testAutowireByType2_1() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInjectAutowire.xml");
        HelloApi helloApi = context.getBean("bean_byType2", HelloApi.class);
        helloApi.sayHello();
    }

    @Test
    public void testAutowireByType2_2() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInjectAutowire.xml");
        HelloApi helloApi = context.getBean("bean_byType3", HelloApi.class);
        helloApi.sayHello();
    }
    @Test
    public void testAutowireByType2_3() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInjectAutowire.xml");
        ListBean listBean = context.getBean("listBean", ListBean.class);
        //对于集合接口根据类型注入将自动查找所有匹配的注入
        Assert.assertTrue(listBean.getList1().size() > 0);
        //对于集合具体类型将根据具体类型进行注入，而不是选择所有泛型类型信息匹配的Bean
        Assert.assertTrue(listBean.getList2() == null);
    }

    @Test
    public void testAutowireByConstructor() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInjectAutowire.xml");
        HelloApi helloApi = context.getBean("bean_constructor", HelloApi.class);
        helloApi.sayHello();
    }

//    @Test
//    public void testAutowireByAutoDetect() throws IOException {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ioc/instantiatingBeanInjectAutowire.xml");
//        HelloApi helloApi = context.getBean("bean", HelloApi.class);
//        helloApi.sayHello();
//    }

}
