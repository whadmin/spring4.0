package com.spring.ioc.bean.assemblyBean.configurationAnnotationProcessor;

import com.spring.ioc.bean.assemblyBean.beanObject.annotation.configuration.ConfigurationConfig;
import com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner.TestServiceImpl;
import junit.framework.Assert;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigUtils;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/2 15:21
 */
public class Test {

    @org.junit.Test
    public void useConfigurationAnnotationRegisterBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(ConfigurationConfig.class);
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
}
