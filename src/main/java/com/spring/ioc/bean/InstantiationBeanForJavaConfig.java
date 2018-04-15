package com.spring.ioc.bean;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.javaConig.InstantiatContainer;
import com.spring.ioc.javaConig.InstantiatingBean;
import com.spring.ioc.model.HelloApi;

public class InstantiationBeanForJavaConfig {

    @Test
    public void testInstantiatingBeanByConstructor() {
        // 1.准备spring Java配置文件
        // 2.初始化容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InstantiatingBean.class);
        // 3、从容器中获取Bean
        HelloApi bean1 = context.getBean("bean1", HelloApi.class);
        bean1.sayHello();

        HelloApi bean2 = context.getBean("bean2", HelloApi.class);
        bean2.sayHello();
        
        HelloApi bean2_byRef = context.getBean("bean2_byRef", HelloApi.class);
        bean2_byRef.sayHello();
    }

    @Test
    public void testInstantiatingBeanByStaticFactory() {
        // 1.准备spring Java配置文件
        // 2.初始化容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InstantiatingBean.class);
        // 3、从容器中获取Bean
        HelloApi bean3 = context.getBean("bean3", HelloApi.class);
        bean3.sayHello();
    }

    @Test
    public void testInstantiatingBeanByInstanceFactory() {
        // 1.准备spring Java配置文件
        // 2.初始化容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InstantiatingBean.class);
        // 3、从容器中获取Bean
        HelloApi bean4 = context.getBean("bean4", HelloApi.class);
        bean4.sayHello();

    }
}
