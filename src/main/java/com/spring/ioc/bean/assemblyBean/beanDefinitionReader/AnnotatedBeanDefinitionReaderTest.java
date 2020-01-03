package com.spring.ioc.bean.assemblyBean.beanDefinitionReader;

import com.spring.ioc.bean.assemblyBean.beanObject.annotation.configuration.ConfigurationConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigUtils;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/2 15:22
 */
public class AnnotatedBeanDefinitionReaderTest {

    /**
     * AnnotatedBeanDefinitionReader 负责@Configuration 注解标识的类 装配 到beanFactory
     */
    @Test
    public void useDefinitionReaderRegisterConfigurationBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(ConfigurationConfig.class);
        // 2.初始化容器
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_FACTORY_BEAN_NAME));

        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("configurationConfig"));
        //ClassPathBeanDefinitionScanner 无法解析 @Bean 注解
        assertFalse(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("bean"));
        //ClassPathBeanDefinitionScanner 无法解析 @ComponentScan 注解
        assertFalse(beanFactory.containsBeanDefinition("testService"));
        assertFalse(beanFactory.containsBeanDefinition("testDao"));
        assertFalse(beanFactory.containsBeanDefinition("testCompoment"));
        assertFalse(beanFactory.containsBeanDefinition("testCache"));
        assertFalse(beanFactory.containsBeanDefinition("testAction"));
        assertFalse(beanFactory.containsBeanDefinition("includeFilterBean"));

        //ClassPathBeanDefinitionScanner 无法解析 @Import 注解
        assertFalse(beanFactory.containsBeanDefinition("importConfiguration"));
        assertFalse(beanFactory.containsBeanDefinition("bean2"));
    }

    /**
     * AnnotatedBeanDefinitionReader 核心原理
     */
    @Test
    public void registerConfigurationBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(ConfigurationConfig.class);
        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, "configurationConfig");
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, beanFactory);
        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("configurationConfig"));
    }
}
