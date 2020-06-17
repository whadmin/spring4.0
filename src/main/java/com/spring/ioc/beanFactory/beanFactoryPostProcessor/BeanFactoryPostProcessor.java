package com.spring.ioc.beanFactory.beanFactoryPostProcessor;

import com.spring.ioc.model.HelloApi;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanFactoryPostProcessor {

    @Test
    public void testInstantiatingBeanByConstructor() {
        // 使用构造器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/beanFactoryPostProcessor.xml");
        HelloApi bean1 = beanFactory.getBean("bean", HelloApi.class);
        bean1.sayHello();
    }
}
