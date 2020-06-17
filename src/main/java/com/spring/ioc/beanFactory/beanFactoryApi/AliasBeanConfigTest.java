package com.spring.ioc.beanFactory.beanFactoryApi;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.HelloApi2;

public class AliasBeanConfigTest {

    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AliasBeanConfigTest.class);
        // 根据类型获取bean
        HelloApi2 helloApi = context.getBean(HelloApi2.class);
        helloApi.sayHello();
    }

    @Test
    public void test2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AliasBeanConfigTest.class);
        // 根据id获取bean
        HelloApi bean = context.getBean("auto", HelloApi.class);
        bean.sayHello();

        bean = context.getBean("bean1", HelloApi.class);
        bean.sayHello();

        bean = context.getBean("bean2", HelloApi.class);
        bean.sayHello();
    }

    @Test
    public void test3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AliasBeanConfigTest.class);
        // 根据name获取bean
        HelloApi bean = context.getBean("bean2", HelloApi.class);
        bean.sayHello();
    }

    @Test
    public void test4() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AliasBeanConfigTest.class);
        // 根据id获取bean
        HelloApi bean3 = context.getBean("bean3", HelloApi.class);
        bean3.sayHello();
        // 根据别名获取bean
        HelloApi bean3_alias1 = context.getBean("bean3_alias1", HelloApi.class);
        bean3_alias1.sayHello();
        // 根据id获取bean
        HelloApi bean4 = context.getBean("bean4", HelloApi.class);
        bean4.sayHello();

        String[] bean4Alias = context.getAliases("bean4");
        // 因此别名不能和id一样，如果一样则由IoC容器负责消除冲突
        Assert.assertEquals(0, bean4Alias.length);

    }

    @Test
    public void test5() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("ioc/GetBeanContainer.xml");
        // 根据id获取bean
        HelloApi bean1 = beanFactory.getBean("bean5", HelloApi.class);
        bean1.sayHello();
        // 2根据别名获取bean
        HelloApi alias11 = beanFactory.getBean("bean5_alias11", HelloApi.class);
        alias11.sayHello();
        // 验证确实是四个别名
        String[] bean1Alias = beanFactory.getAliases("bean5");
        System.out.println("=======GetBeanContainer.xml bean5 别名========");
        for (String alias : bean1Alias) {
            System.out.println(alias);
        }
        System.out.println("=======GetBeanContainer.xml bean5 别名========");
        Assert.assertEquals(4, bean1Alias.length);
    }

}
