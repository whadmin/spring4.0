package com.spring.ioc.bean.getBean.propertyInjection.application.core;

import com.spring.ioc.bean.getBean.propertyInjection.beanObject.no_annotation.IntegerTestBean;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 23:00
 */
public class XmlNoAutowireBeanInjectCore {

    @Test
    public void testInstantiatingBeanByInject1() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义String 类型注册值
        TypedStringValue typedStringValue = new TypedStringValue("4");
        typedStringValue.setSource(null);

        // 3 定义PropertyValue 设置 TypedStringValue
        PropertyValue pv = new PropertyValue("index", typedStringValue);
        pv.setSource(null);

        // 4 定义beanDefinition 设置 PropertyValue
        AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null,
                "com.spring.ioc.bean.getBean.propertyInjection.beanObject.no_annotation.IntegerTestBean", null);
        beanDefinition.getPropertyValues().addPropertyValue(pv);

        // 5 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition, "bean4_1_at", null),
                beanFactory);

        BeanDefinition bean4_1_at = beanFactory.getBeanDefinition("bean4_1_at");
        System.err.println(ReflectionToStringBuilder.toString(bean4_1_at, ToStringStyle.MULTI_LINE_STYLE));

        Resource resource = new ClassPathResource("ioc/bean/getBean/methodInjection/beanNoAutowireBean.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        BeanDefinition bean4_1 = beanFactory.getBeanDefinition("bean4_1");
        System.err.println(ReflectionToStringBuilder.toString(bean4_1, ToStringStyle.MULTI_LINE_STYLE));

        IntegerTestBean integerTestBean1 = beanFactory.getBean("bean4_1_at", IntegerTestBean.class);
        integerTestBean1.sayHello();
        IntegerTestBean integerTestBean2 = beanFactory.getBean("bean4_1", IntegerTestBean.class);
        integerTestBean2.sayHello();
    }



    @Test
    public void testInstantiatingBeanByInject2() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义Ref 类型注册值
        RuntimeBeanReference runtimeBeanReference = new RuntimeBeanReference("index");
        runtimeBeanReference.setSource(null);

        // 3 定义PropertyValue 设置 RuntimeBeanReference
        PropertyValue pv = new PropertyValue("index", runtimeBeanReference);
        pv.setSource(null);

        // 4 定义beanDefinition 设置 PropertyValue
        AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null,
                "com.spring.ioc.bean.getBean.propertyInjection.beanObject.no_annotation.IntegerTestBean", null);
        beanDefinition.getPropertyValues().addPropertyValue(pv);

        // 5 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition, "bean4_2_at", null),
                beanFactory);

        BeanDefinition idrefBean1_at = beanFactory.getBeanDefinition("bean4_2_at");
        System.err.println(ReflectionToStringBuilder.toString(idrefBean1_at, ToStringStyle.MULTI_LINE_STYLE));

        Resource resource = new ClassPathResource("ioc/bean/getBean/methodInjection/beanNoAutowireBean.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        BeanDefinition idrefBean1 = beanFactory.getBeanDefinition("bean4_2");
        System.err.println(ReflectionToStringBuilder.toString(idrefBean1, ToStringStyle.MULTI_LINE_STYLE));

        IntegerTestBean idRefTestBean1 = beanFactory.getBean("bean4_2_at", IntegerTestBean.class);
        System.err.println(idRefTestBean1);
        IntegerTestBean idRefTestBean2 = beanFactory.getBean("bean4_2", IntegerTestBean.class);
        System.err.println(idRefTestBean2);
    }
}
