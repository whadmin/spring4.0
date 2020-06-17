package com.spring.ioc.beanFactory.beanFactoryApi;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HierarchicalBeanFactoryAPITest {

    @Test
    public void testgetParentBeanFactory() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/beanFactory/beanFactoryApi.xml");
        ConfigurableListableBeanFactory beanFactory = ((ClassPathXmlApplicationContext) context).getBeanFactory();
        Assert.assertNull(beanFactory.getParentBeanFactory());
    }

    @Test
    public void testContainsLocalBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/beanFactory/beanFactoryApi.xml");
        ConfigurableListableBeanFactory beanFactory = ((ClassPathXmlApplicationContext) context).getBeanFactory();
        Assert.assertNotNull(beanFactory.containsLocalBean("simpleBean"));
    }
}
