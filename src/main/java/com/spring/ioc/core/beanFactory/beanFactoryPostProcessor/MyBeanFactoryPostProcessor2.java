package com.spring.ioc.core.beanFactory.beanFactoryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

public class MyBeanFactoryPostProcessor2 implements BeanFactoryPostProcessor, Ordered {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("调用 BeanFactoryPostProcessor_1 ...");
        // 获取指定的 BeanDefinition
        BeanDefinition bd = beanFactory.getBeanDefinition("bean");
        MutablePropertyValues pvs = bd.getPropertyValues();
        pvs.addPropertyValue("message", "message2");
    }

    @Override
    public int getOrder() {
        return 2;
    }
}