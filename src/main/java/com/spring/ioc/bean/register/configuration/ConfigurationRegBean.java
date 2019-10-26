package com.spring.ioc.bean.register.configuration;

import com.spring.ioc.bean.register.beanObject.configuration.ConfigurationConfig;
import com.spring.ioc.bean.register.beanObject.configuration.ConfigurationScannerConfig;
import com.spring.ioc.bean.register.beanObject.scanner.TestServiceImpl;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;

import static org.junit.Assert.*;

import java.util.Arrays;

public class ConfigurationRegBean {

    @Before
    public void init() {
    }

    @After
    public void destory() {
    }

    @Test
    public void registerConfigurationBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(ConfigurationConfig.class);
        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, "configurationConfig");
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, beanFactory);
        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("configurationConfig"));
    }

    @Test
    public void useDefinitionReaderRegisterConfigurationBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(ConfigurationConfig.class);
        reader.register(ConfigurationScannerConfig.class);
        // 2.初始化容器
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_FACTORY_BEAN_NAME));

        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("configurationConfig"));
        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("configurationScannerConfig"));
        assertFalse(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("otherConfigurationConfig"));
        assertFalse(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("bean"));
    }


    @Test
    public void useProcessorRegisterBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(ConfigurationConfig.class);
        reader.register(ConfigurationScannerConfig.class);
        BeanDefinitionRegistryPostProcessor postProcessor = beanFactory.getBean(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, BeanDefinitionRegistryPostProcessor.class);
        postProcessor.postProcessBeanDefinitionRegistry(beanFactory);

        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("configurationConfig"));
        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("configurationScannerConfig"));
        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("otherConfigurationConfig"));
        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("bean"));

        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_FACTORY_BEAN_NAME));

        assertTrue(beanFactory.containsBeanDefinition("testService"));
        assertTrue(beanFactory.containsBeanDefinition("testDao"));
        assertTrue(beanFactory.containsBeanDefinition("testCompoment"));
        assertTrue(beanFactory.containsBeanDefinition("testCache"));
        assertTrue(beanFactory.containsBeanDefinition("testAction"));
        assertTrue(beanFactory.containsBeanDefinition("includeFilterBean"));
        assertFalse(beanFactory.containsBeanDefinition("excludeFilterBean"));

        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = beanFactory.getBean(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME, AutowiredAnnotationBeanPostProcessor.class);
        autowiredAnnotationBeanPostProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);
        TestServiceImpl service = beanFactory.getBean("testService", TestServiceImpl.class);
        Assert.assertNotNull(service.getDao());
    }


    @Test
    public void useApplicationContextBean() {
        AnnotationConfigApplicationContext beanFactory = new AnnotationConfigApplicationContext(new Class[]{ConfigurationConfig.class,ConfigurationScannerConfig.class});

        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("configurationConfig"));
        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("configurationScannerConfig"));
        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("otherConfigurationConfig"));
        assertTrue(Arrays.toString(beanFactory.getBeanDefinitionNames()).contains("bean"));

        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_FACTORY_BEAN_NAME));

        assertTrue(beanFactory.containsBeanDefinition("testService"));
        assertTrue(beanFactory.containsBeanDefinition("testDao"));
        assertTrue(beanFactory.containsBeanDefinition("testCompoment"));
        assertTrue(beanFactory.containsBeanDefinition("testCache"));
        assertTrue(beanFactory.containsBeanDefinition("testAction"));
        assertTrue(beanFactory.containsBeanDefinition("includeFilterBean"));
        assertFalse(beanFactory.containsBeanDefinition("excludeFilterBean"));

        TestServiceImpl service = beanFactory.getBean("testService", TestServiceImpl.class);
        Assert.assertNotNull(service.getDao());
    }
}
