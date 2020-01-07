package com.spring.ioc.bean.assemblyBean.beanDefinitionReader.configurationAnnotationProcessor;

import com.spring.ioc.bean.assemblyBean.javaConfig.ConfigurationConfig;
import com.spring.ioc.bean.assemblyBean.javaConfig.EnableConfigurationConfig;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/7 22:34
 */
public class ConfigurationAnnotationProcessorTest {

    /**
     * ConfigurationClassPostProcessor 负责解析处理@Configuration 注解标识的类中的如下注解
     *
     * @Bean 注解
     * @ComponentScan 注解
     * @Import 注解
     *
     * 其内部使用了
     * ConfigurationClassBeanDefinitionReader
     */
    @Test
    public void useDefinitionReaderRegisterConfigurationBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(ConfigurationConfig.class);

        ConfigurationClassPostProcessor configurationClassPostProcessor = beanFactory.getBean(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, ConfigurationClassPostProcessor.class);
        configurationClassPostProcessor.postProcessBeanFactory(beanFactory);

        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("configurationConfig"));
        //ClassPathBeanDefinitionScanner 无法解析 @Bean 注解
        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("bean"));
        //ClassPathBeanDefinitionScanner 无法解析 @ComponentScan 注解
        assertTrue(beanFactory.containsBeanDefinition("testService"));
        assertTrue(beanFactory.containsBeanDefinition("testDao"));
        assertTrue(beanFactory.containsBeanDefinition("testCompoment"));
        assertTrue(beanFactory.containsBeanDefinition("testCache"));
        assertTrue(beanFactory.containsBeanDefinition("testAction"));
        assertTrue(beanFactory.containsBeanDefinition("includeFilterBean"));

        //ClassPathBeanDefinitionScanner 无法解析 @Import 注解
        assertTrue(beanFactory.containsBeanDefinition("importConfiguration"));
        assertTrue(beanFactory.containsBeanDefinition("bean2"));
    }


    @Test
    public void useDefinitionReaderRegisterConfigurationBean2() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(EnableConfigurationConfig.class);

        ConfigurationClassPostProcessor configurationClassPostProcessor = beanFactory.getBean(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, ConfigurationClassPostProcessor.class);
        configurationClassPostProcessor.postProcessBeanFactory(beanFactory);


        //ClassPathBeanDefinitionScanner 无法解析 @Import 注解
        assertTrue(beanFactory.containsBeanDefinition("importConfiguration"));
        assertTrue(beanFactory.containsBeanDefinition("bean2"));
    }
}
