package com.spring.ioc.bean.getBean.build.application.core;


import com.spring.ioc.bean.getBean.build.beanObject.no_annotation.AutowireByConstructorMultipleMatches;
import com.spring.ioc.bean.getBean.build.beanObject.no_annotation.ConstructorHaveParamBean;
import com.spring.ioc.bean.getBean.build.beanObject.no_annotation.ConstructorNoParamBean;
import com.spring.ioc.bean.getBean.propertyInjection.beanObject.no_annotation.AutowireByNameBean;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.spring.ioc.model.HelloApi;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * xml构造方法创建bean,API实现
 */
public class ConstructorBuildBeanCoreTest {

    @Test
    public void readSouceCode() {
        Resource resource = new ClassPathResource("ioc/bean/getBean/build/constructorBuildBean.xml");
        // 2.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

//		System.out.println(Arrays.toString(beanFactory.getBeanDefinitionNames()));
//		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
//			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
//			System.out.println(ReflectionToStringBuilder.toString(beanDefinition, ToStringStyle.MULTI_LINE_STYLE));
//		}
    }

    @Test
    public void testConstructorDefaultBean() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        /* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
        AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.build.beanObject.no_annotation.ConstructorNoParamBean",
                null);
        // 设置bean的初始化方法
        beanDefinition1.setInitMethodName("init");
        // 设置bean的初始化方法开启
        beanDefinition1.setEnforceInitMethod(true);
        // 设置bean的销毁方法
        beanDefinition1.setDestroyMethodName("destroy");
        // 设置bean的销毁方法开启
        beanDefinition1.setEnforceDestroyMethod(true);
        // 设置beanDefinition1 构造来源 Xml 或 Java对象
        beanDefinition1.setResource(null);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition1, "constructorDefaultBean_at", null),
                beanFactory);
        // 4 获取注册BeanDefinition
        System.out.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("constructorDefaultBean_at"),
                ToStringStyle.MULTI_LINE_STYLE));

        ConstructorNoParamBean bean1 = beanFactory.getBean("constructorDefaultBean_at", ConstructorNoParamBean.class);
        assertThat(bean1.getMessage()).isNotNull();
    }

    @Test
    public void testConstructorHaveParamBean1()
            throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        /* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
        AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.build.beanObject.no_annotation.ConstructorHaveParamBean",
                null);
        // 设置bean的初始化方法
        beanDefinition.setInitMethodName("init");
        // 设置bean的初始化方法开启
        beanDefinition.setEnforceInitMethod(true);
        // 设置bean的销毁方法
        beanDefinition.setDestroyMethodName("destroy");
        // 设置bean的销毁方法开启
        beanDefinition.setEnforceDestroyMethod(true);
        // 设置beanDefinition1 构造来源 Xml 或 Java对象
        beanDefinition.setResource(null);

        // 定义普通属性
        TypedStringValue object1 = new TypedStringValue("Hello World!");
        object1.setSource(null);
        ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
                object1);

        // 设置构造bean的参数按构造函数索引
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(
                0, valueHolder1);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition, "constructorParamBean_byIndex_at", null),
                beanFactory);
        // 4 获取注册BeanDefinition
        System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("constructorParamBean_byIndex_at"),
                ToStringStyle.MULTI_LINE_STYLE));

        ConstructorHaveParamBean bean1 = beanFactory.getBean("constructorParamBean_byIndex_at", ConstructorHaveParamBean.class);
        assertThat(bean1.getMessage()).isNotNull();
    }

    @Test
    public void testConstructorHaveParamBean2()
            throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        /* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
        AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.build.beanObject.no_annotation.ConstructorHaveParamBean",
                null);
        // 设置bean的初始化方法
        beanDefinition.setInitMethodName("init");
        // 设置bean的初始化方法开启
        beanDefinition.setEnforceInitMethod(true);
        // 设置bean的销毁方法
        beanDefinition.setDestroyMethodName("destroy");
        // 设置bean的销毁方法开启
        beanDefinition.setEnforceDestroyMethod(true);
        // 设置beanDefinition1 构造来源 Xml 或 Java对象
        beanDefinition.setResource(null);

        // 定义普通属性
        /** 定义普通属性,并设置属性类型 **/
        TypedStringValue object1 = new TypedStringValue("Hello World!");
        object1.setSource(null);
        ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
                object1);
        valueHolder1.setType("java.lang.String");

        // 设置构造bean的参数按构造函数索引
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(
                valueHolder1);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition, "constructorParamBean_byType_at", null),
                beanFactory);
        // 4 获取注册BeanDefinition
        System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("constructorParamBean_byType_at"),
                ToStringStyle.MULTI_LINE_STYLE));

        ConstructorHaveParamBean bean1 = beanFactory.getBean("constructorParamBean_byType_at", ConstructorHaveParamBean.class);
        assertThat(bean1.getMessage()).isNotNull();
    }

    @Test
    public void testtestConstructorHaveParamBean3() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        /* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
        AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.build.beanObject.no_annotation.ConstructorHaveParamBean",
                null);
        // 设置bean的初始化方法
        beanDefinition.setInitMethodName("init");
        // 设置bean的初始化方法开启
        beanDefinition.setEnforceInitMethod(true);
        // 设置bean的销毁方法
        beanDefinition.setDestroyMethodName("destroy");
        // 设置bean的销毁方法开启
        beanDefinition.setEnforceDestroyMethod(true);
        // 设置beanDefinition1 构造来源 Xml 或 Java对象
        beanDefinition.setResource(null);

        // 定义普通属性
        /** 定义普通属性,并设置属性类型 **/
        TypedStringValue object1 = new TypedStringValue("Hello World!");
        object1.setSource(null);
        ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
                object1);
        valueHolder1.setName("message");

        // 设置构造bean的参数按构造函数索引
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(
                valueHolder1);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition, "constructorParamBean_byName_at", null),
                beanFactory);
        // 4 获取注册BeanDefinition
        System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("constructorParamBean_byName_at"),
                ToStringStyle.MULTI_LINE_STYLE));

        ConstructorHaveParamBean bean2 = beanFactory.getBean("constructorParamBean_byName_at", ConstructorHaveParamBean.class);
        assertThat(bean2.getMessage()).isNotNull();
    }

    @Test
    public void testConstructorHaveParamBean14() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        /* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
        AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.build.beanObject.no_annotation.ConstructorHaveParamBean",
                null);
        // 设置bean的初始化方法
        beanDefinition.setInitMethodName("init");
        // 设置bean的初始化方法开启
        beanDefinition.setEnforceInitMethod(true);
        // 设置bean的销毁方法
        beanDefinition.setDestroyMethodName("destroy");
        // 设置bean的销毁方法开启
        beanDefinition.setEnforceDestroyMethod(true);
        // 设置beanDefinition1 构造来源 Xml 或 Java对象
        beanDefinition.setResource(null);

        // 定义普通属性
        /** 定义普通属性,并设置属性类型 **/
        RuntimeBeanReference object1 = new RuntimeBeanReference("message");
        object1.setSource(null);
        ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
                object1);
        valueHolder1.setName("message");

        // 设置构造bean的参数按构造函数索引
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(
                valueHolder1);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition, "constructorParamBean_byRef_at", null),
                beanFactory);
        // 4 获取注册BeanDefinition
        System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("constructorParamBean_byRef_at"),
                ToStringStyle.MULTI_LINE_STYLE));


        Resource resource = new ClassPathResource("ioc/bean/getBean/build/constructorBuildBean.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        ConstructorHaveParamBean bean2 = beanFactory.getBean("constructorParamBean_byRef_at", ConstructorHaveParamBean.class);
        assertThat(bean2.getMessage()).isNotNull();
    }

    @Test
    public void testAutowireByConstructor() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        /* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
        AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.build.beanObject.no_annotation.AutowireByConstructorMultipleMatches",
                null);
        beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils
                .registerBeanDefinition(new BeanDefinitionHolder(beanDefinition, "autowireByConstructorMultiple_at", null), beanFactory);

        BeanDefinition bean_byName_at = beanFactory.getBeanDefinition("autowireByConstructorMultiple_at");
        System.err.println(ReflectionToStringBuilder.toString(bean_byName_at, ToStringStyle.MULTI_LINE_STYLE));


        Resource resource = new ClassPathResource("ioc/bean/getBean/build/autowireByConstructorPrimary.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        BeanDefinition bean_byName = beanFactory.getBeanDefinition("autowireByConstructorMultiple");
        System.err.println(ReflectionToStringBuilder.toString(bean_byName, ToStringStyle.MULTI_LINE_STYLE));

        AutowireByConstructorMultipleMatches bean1 = beanFactory.getBean("autowireByConstructorMultiple", AutowireByConstructorMultipleMatches.class);
        assertThat(bean1.getDataSource()).isNotNull();

        AutowireByConstructorMultipleMatches bean2 = beanFactory.getBean("autowireByConstructorMultiple_at", AutowireByConstructorMultipleMatches.class);
        assertThat(bean2.getDataSource()).isNotNull();

    }
}
