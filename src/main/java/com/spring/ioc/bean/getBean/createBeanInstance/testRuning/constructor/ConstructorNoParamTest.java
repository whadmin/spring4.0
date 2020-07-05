package com.spring.ioc.bean.getBean.createBeanInstance.testRuning.constructor;

import com.spring.BaseTest;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.annotation.AutowireByConstructorQualifierBean;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.AutowireByConstructorMultipleMatches;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.AutowireByConstructor;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorNoParamBean;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorHaveParamBean;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * 通过XML配置，调用构造函数创建Bean实例
 */
public class ConstructorNoParamTest extends BaseTest {

    /**
     * 使用默认构造函数实例化bean
     */
    @Test
    public void testConstructorNoParamBean() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/createBeanInstance/constructorBuildBean.xml");

        // 3 使用默认构造函数实例化bean
        ConstructorNoParamBean bean = beanFactory.getBean("constructorDefaultBean", ConstructorNoParamBean.class);
        assertThat(bean.getMessage()).isNotNull();
    }







}
