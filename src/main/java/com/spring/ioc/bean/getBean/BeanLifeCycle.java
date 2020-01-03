package com.spring.ioc.bean.getBean;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class BeanLifeCycle {

    @Test
    public void testInstantiatingBeanByConstructor() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        Resource resource = new ClassPathResource("ioc/bean/getBean/lifeCycleBean.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        LifeCycleBean lifeCycleBean = (LifeCycleBean) beanFactory.getBean("lifeCycle");
        lifeCycleBean.display();

        System.out.println("方法调用完成，容器开始关闭....");
        beanFactory.destroySingletons();
    }
}
