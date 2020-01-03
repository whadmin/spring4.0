package com.spring.ioc.beanFactory;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.HelloApi2;


public class GetBeanContainer {

    @Test
    public void test1() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/getBeanMethod.xml");
        // 根据类型获取bean
        HelloApi2 helloApi = beanFactory.getBean(HelloApi2.class);
        helloApi.sayHello();
    }

    @Test
    public void test2() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/getBeanMethod.xml");
        // 根据id获取bean
        HelloApi bean = beanFactory.getBean("bean1", HelloApi.class);
        bean.sayHello();
    }

    @Test
    public void test3() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/getBeanMethod.xml");
        // 根据name获取bean
        HelloApi bean = beanFactory.getBean("bean2", HelloApi.class);
        bean.sayHello();
    }

    @Test
    public void test4() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/getBeanMethod.xml");
        // 根据id获取bean
        HelloApi bean3 = beanFactory.getBean("bean3", HelloApi.class);
        bean3.sayHello();
        // 根据别名获取bean
        HelloApi bean3_alias1 = beanFactory.getBean("bean3_alias1", HelloApi.class);
        bean3_alias1.sayHello();
        // 根据id获取bean
        HelloApi bean4 = beanFactory.getBean("bean4", HelloApi.class);
        bean4.sayHello();

        String[] bean4Alias = beanFactory.getAliases("bean4");
        // 因此别名不能和id一样，如果一样则由IoC容器负责消除冲突
        Assert.assertEquals(0, bean4Alias.length);

    }

    @Test
    public void test5() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/getBeanMethod.xml");
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

        // 根据id获取bean
        HelloApi bean6 = beanFactory.getBean("bean6", HelloApi.class);
        bean6.sayHello();
        // //2根据别名获取bean
        HelloApi bean6_alias21 = beanFactory.getBean("bean6_alias21", HelloApi.class);
        bean6_alias21.sayHello();
        // 验证确实是两个别名
        String[] bean6Alias = beanFactory.getAliases("bean6");
        System.out.println("=======namingbean5.xml bean2 别名========");
        for (String alias : bean6Alias) {
            System.out.println(alias);
        }
        System.out.println("=======namingbean5.xml bean2 别名========");
        Assert.assertEquals(2, bean6Alias.length);

    }

    @Test
    public void test6() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/getBeanMethod.xml");
        // 根据id获取bean
        HelloApi bean = beanFactory.getBean("bean7", HelloApi.class);
        bean.sayHello();

        // 根据别名获取bean
        HelloApi alias1 = beanFactory.getBean("bean7_alias1", HelloApi.class);
        alias1.sayHello();
        HelloApi alias2 = beanFactory.getBean("bean7_alias2", HelloApi.class);
        alias2.sayHello();

        String[] beanAlias = beanFactory.getAliases("bean7");
        System.out.println("=======namingbean6.xml bean 别名========");
        for (String alias : beanAlias) {
            System.out.println(alias);
        }
        System.out.println("=======namingbean6.xml bean 别名========");
        Assert.assertEquals(2, beanAlias.length);

    }

}
