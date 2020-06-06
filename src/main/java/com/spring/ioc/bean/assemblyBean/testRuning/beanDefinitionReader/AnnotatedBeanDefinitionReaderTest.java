package com.spring.ioc.bean.assemblyBean.testRuning.beanDefinitionReader;

import com.spring.ioc.bean.assemblyBean.javaConfig.ConfigurationClassAnnBeanConfig1;
import com.spring.ioc.bean.assemblyBean.javaConfig.ConfigurationClassAnnComponentScanConfig;
import com.spring.ioc.bean.assemblyBean.javaConfig.ConfigurationClassAnnImportConfig1;
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
 *
 * 使用AnnotatedBeanDefinitionReader装配Bean
 * 1  装配注解处理器Bean
 * 2  装配指定注解配置类
 *
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
        reader.register(ConfigurationClassAnnBeanConfig1.class);
        reader.register(ConfigurationClassAnnImportConfig1.class);
        reader.register(ConfigurationClassAnnComponentScanConfig.class);

        //装配注解处理器Bean
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_FACTORY_BEAN_NAME));
        //装配指定注解配置类（这里必须被@Configuration注解或派送注解修饰）
        assertTrue(beanFactory.containsBeanDefinition("configurationClassAnnBeanConfig1"));
        assertTrue(beanFactory.containsBeanDefinition("configurationClassAnnImportConfig1"));
        assertTrue(beanFactory.containsBeanDefinition("configurationClassAnnComponentScanConfig"));

        //ClassPathBeanDefinitionScanner 无法解析 @Bean 注解
        assertFalse(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("bean1"));
        //ClassPathBeanDefinitionScanner 无法解析 @ComponentScan 注解
        assertFalse(beanFactory.containsBeanDefinition("testService"));
        assertFalse(beanFactory.containsBeanDefinition("testDao"));
        assertFalse(beanFactory.containsBeanDefinition("testCompoment"));
        assertFalse(beanFactory.containsBeanDefinition("testCache"));
        assertFalse(beanFactory.containsBeanDefinition("testAction"));
        assertFalse(beanFactory.containsBeanDefinition("includeFilterBean"));
        //ClassPathBeanDefinitionScanner 无法解析 @Import 注解
        assertFalse(beanFactory.containsBeanDefinition("configurationClassAnnBeanConfig2"));
        assertFalse(beanFactory.containsBeanDefinition("bean2"));
    }

    /**
     * AnnotatedBeanDefinitionReader 核心原理
     */
    @Test
    public void registerConfigurationBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(ConfigurationClassAnnBeanConfig1.class);
        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, "configurationConfig");
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, beanFactory);
        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("configurationConfig"));
    }
}
