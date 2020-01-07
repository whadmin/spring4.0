package com.spring.ioc.bean.assemblyBean.application;

import com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner.TestServiceImpl;
import junit.framework.Assert;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/7 22:12
 */
public class ClassPathXmlApplicationContextTest {

    @Test
    public void testClassPathXmlApplicationContext() {
        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("ioc/bean/register/registerScannerAnnBean.xml");

        assertTrue(beanFactory.getBeanFactory().containsBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.getBeanFactory().containsBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.getBeanFactory().containsBeanDefinition(AnnotationConfigUtils.REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.getBeanFactory().containsBeanDefinition(AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.getBeanFactory().containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.getBeanFactory().containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_FACTORY_BEAN_NAME));

        assertTrue(beanFactory.getBeanFactory().containsBeanDefinition("testService"));
        assertTrue(beanFactory.getBeanFactory().containsBeanDefinition("testDao"));
        assertTrue(beanFactory.getBeanFactory().containsBeanDefinition("testCompoment"));
        assertTrue(beanFactory.getBeanFactory().containsBeanDefinition("testCache"));
        assertTrue(beanFactory.getBeanFactory().containsBeanDefinition("testAction"));
        assertTrue(beanFactory.getBeanFactory().containsBeanDefinition("includeFilterBean"));
        assertFalse(beanFactory.containsBeanDefinition("excludeFilterBean"));
        System.out.println(ReflectionToStringBuilder.toString(beanFactory.getBeanFactory().getBeanDefinition("testService")));

        TestServiceImpl service = beanFactory.getBean("testService", TestServiceImpl.class);
        Assert.assertNotNull(service.getDao());
    }
}
