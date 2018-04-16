package com.spring.ioc.bean.javaConfig;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.javaConig.InstantiatContainer;
import com.spring.ioc.javaConig.InstantiatingBean;
import com.spring.ioc.model.HelloApi;


/**
 * 
 * @author wuhao.w
 *
 */
public class InstantiationBeanForJavaConfig {

    @Test
    public void testInstantiatingBeanByConstructor() {
        // 1.准备spring Java配置文件
        // 2.初始化容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InstantiatingBean.class);
        // 3、从容器中获取Bean
        HelloApi bean1 = context.getBean("bean1", HelloApi.class);
        bean1.sayHello();
    }
    
    @Test
    public void testInstantiatingBeanByConstructor1() {
        // 1.准备spring Java配置文件
        // 2.初始化容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InstantiatingBean.class);
        // 3、从容器中获取Bean
        HelloApi bean1 = context.getBean("bean2", HelloApi.class);
        bean1.sayHello();
    }
}
