package com.spring.ioc.bean.getBean.createBeanInstance.application;

import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorNoParamBean;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorHaveParamBean;
import com.spring.ioc.bean.getBean.createBeanInstance.javaConfig.ConfigurationBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 使用实例工厂实例化bean
 * @Author: wuhao.w
 * @Date: 2020/1/5 16:59
 */
public class InstanceFactoryBuildBeanTest {


    /**
     * 通过xml配置，使用实例工厂实例化bean
     */
    @Test
    public void testInstanceFactoryBuildBeanXml() {
        // 使用实例工厂方法
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/createBeanInstance/instanceFactoryBuildBean.xml");
        // 获取根据参数索引依赖注入的Bean
        ConstructorHaveParamBean byIndex = context.getBean("instanceFactoryBuildBean_byIndex", ConstructorHaveParamBean.class);
        assertThat(byIndex.getMessage()).isNotNull();

        // 获取根据参数类型依赖注入的Bean
        ConstructorHaveParamBean byType = context.getBean("instanceFactoryBuildBean_byType", ConstructorHaveParamBean.class);
        assertThat(byType.getMessage()).isNotNull();

        // 获取根据参数名字依赖注入的Bean
        ConstructorHaveParamBean byName = context.getBean("instanceFactoryBuildBean_byName", ConstructorHaveParamBean.class);
        assertThat(byName.getMessage()).isNotNull();
    }

    /**
     * 通过ConfigurationBean配置本质是使用实例工厂实例化bean
     */
    @Test
    public void testInstanceFactoryBuildBeanJavaConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                ConfigurationBean.class);
        // 获取根据参数名字依赖注入的Bean
        ConstructorNoParamBean byName = context.getBean("buildBean", ConstructorNoParamBean.class);
        assertThat(byName.getMessage()).isNotNull();
    }
}
