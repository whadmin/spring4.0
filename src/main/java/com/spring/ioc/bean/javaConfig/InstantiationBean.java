package com.spring.ioc.bean.javaConfig;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.spring.ioc.bean.getBean.createBeanInstance.javaConfig.ConfigurationBean;
import com.spring.ioc.model.HelloApi;


/**
 * 
 *  通过使用JavaConfig做为SpringIOC容器的描述
 *  
 *  所有bean的构造都是通过实例工厂的方式，工厂对象为容器对象InstantiatingBean,构造方法为@bean 注解的方法，自动注入的方式默认为AUTOWIRE_CONSTRUCTOR
 *
 */
public class InstantiationBean {

    @Test
    public void testInstantiatingBeanByConstructor() {
        // 1.准备spring Java配置文件
        // 2.初始化容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationBean.class);
        // 3、从容器中获取Bean
        HelloApi bean1 = context.getBean("bean1", HelloApi.class);
        bean1.sayHello();
    }
    
    @Test
    public void testInstantiatingBeanByConstructor1() {
        // 1.准备spring Java配置文件
        // 2.初始化容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationBean.class);
        // 3、从容器中获取Bean
        HelloApi bean1 = context.getBean("bean2", HelloApi.class);
        bean1.sayHello();
    }
}
