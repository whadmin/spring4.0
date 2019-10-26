package com.spring.ioc.bean.register.scanner;

import com.spring.ioc.bean.register.beanObject.scanner.IncludeFilter;
import com.spring.ioc.bean.register.beanObject.scanner.TestServiceImpl;
import com.spring.ioc.bean.register.beanObject.scanner.ExcludeFilter;
import junit.framework.Assert;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ScannerRegBean {

    @Test
    public void registerScannerBean() throws IOException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

        String testServiceImplPath =ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath("com.spring.ioc.bean.register.beanObject.scanner") + '/' + "TestCompoment.class";
        Resource[] resources1 = resourcePatternResolver.getResources(testServiceImplPath);
        MetadataReader metadataReader = new CachingMetadataReaderFactory().getMetadataReader(resources1[0]);

        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
        sbd.setResource(resources1[0]);
        sbd.setSource(resources1[0]);
        String beanName = beanNameGenerator.generateBeanName(sbd,beanFactory);
        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(sbd, beanName);
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, beanFactory);

        assertTrue(beanFactory.containsBeanDefinition("testCompoment"));
    }

    @Test
    public void useClassPathBeanDefinitionScannerRegisterBean() {
        // 2.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.addExcludeFilter(new AnnotationTypeFilter(ExcludeFilter.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(IncludeFilter.class));
        scanner.scan("com.spring.ioc.bean.register.beanObject.scanner");

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
