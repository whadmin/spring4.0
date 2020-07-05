package com.spring;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;

/**
 * @Author: wuhao.w
 * @Date: 2020/6/17 12:17
 */
public class BaseTest {

    /**
     * xml配置文件装配Bean
     */
    public void xmlAssembly(DefaultListableBeanFactory beanFactory, String path) {
        Resource resource = new ClassPathResource(path);
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
    }

    /**
     * Java注解类装配Bean
     */
    public void annotatedBeanAssembly(DefaultListableBeanFactory beanFactory, Class className) {
        // 2 装配Bean
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(className);

        //使用ConfigurationClassPostProcessor
        //从已装配Bean中去读取被@Configuration注解或派送注解修饰的类作为注解配置类
        //对注解配置类中Bean实现装配
        ConfigurationClassPostProcessor configurationClassPostProcessor = beanFactory.getBean(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, ConfigurationClassPostProcessor.class);
        configurationClassPostProcessor.postProcessBeanFactory(beanFactory);

        beanFactory.removeBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_PROCESSOR_BEAN_NAME);
    }




}
