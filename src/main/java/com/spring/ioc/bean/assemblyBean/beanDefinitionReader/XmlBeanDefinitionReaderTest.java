package com.spring.ioc.bean.assemblyBean.beanDefinitionReader;

import com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner.TestServiceImpl;
import junit.framework.Assert;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.junit.Assert.*;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/2 15:25
 */
public class XmlBeanDefinitionReaderTest {

    /**
     * 手动创建 XmlBeanDefinitionReader读取XML生成 BeanDefinition，注册到BeanFactory
     */
    @Test
    public void registerXmlBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClassName("com.spring.ioc.bean.assemblyBean.beanObject.1");
        // 设置bean的初始化方法
        bd.setInitMethodName("init");
        // 设置bean的初始化方法开启
        bd.setEnforceInitMethod(false);
        // 设置bean的销毁方法
        bd.setDestroyMethodName("destroy");
        // 设置bean的销毁方法开启
        bd.setEnforceDestroyMethod(false);
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(bd, "bean", null),
                beanFactory);
        assertTrue(beanFactory.containsBeanDefinition("bean"));
        Object bean = beanFactory.getBean("bean");
        System.out.println(bean);
    }

    /**
     * XmlBeanDefinitionReader读取XML生成 BeanDefinition，注册到BeanFactory
     */
    @Test
    public void useXmlBeanDefinitionReaderRegisterBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        Resource resource = new ClassPathResource("ioc/bean/register/registerXmlBean.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        assertEquals(beanFactory.getBeanDefinitionNames().length, 1);
        assertTrue(beanFactory.containsBeanDefinition("bean"));
    }

    /**
     * 配置 <context:annotation-config/> 会自动添加 注解处理 BeanProcessor
     */
    @Test
    public void registerAnnotationBeanProcessor() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        Resource resource = new ClassPathResource("ioc/bean/register/registerAnnitationConfig.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_FACTORY_BEAN_NAME));
    }


    /**
     * 配置 <context:component-scan/>
     *   1 会自动添加 注解处理 BeanProcessor
     *   2 会创建ClassPathBeanDefinitionScanner 扫描配置的包路径下定义的Bean
     *   3 适用@AUTOWIRED 注解生效需要将 AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME
     *     创建并添加 beanFactory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor)
     */
    @Test
    public void useClassPathBeanDefinitionScannerRegisterBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        Resource resource = new ClassPathResource("ioc/bean/register/registerScannerAnnBean.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

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
        System.out.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("testService")));

        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = beanFactory.getBean(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME, AutowiredAnnotationBeanPostProcessor.class);
        autowiredAnnotationBeanPostProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);
        TestServiceImpl service = beanFactory.getBean("testService", TestServiceImpl.class);
        Assert.assertNotNull(service.getDao());
    }




}
