package com.spring.ioc.bean.assemblyBean.beanDefinitionReader;

import com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner.ExcludeFilter;
import com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner.IncludeFilter;
import com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner.TestServiceImpl;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
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

/**
 * @Author: wuhao.w
 * @Date: 2020/1/2 15:23
 * 通过 ClassPathBeanDefinitionScanner 扫描指定路径包 注册(装配)Bean
 */
public class ClassPathBeanDefinitionScannerTest {

    /**
     * 通过 ClassPathBeanDefinitionScanner 扫描指定路径包 注册(装配)Bean
     */
    @Test
    public void useClassPathBeanDefinitionScannerRegisterBean() {
        // 2.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.addExcludeFilter(new AnnotationTypeFilter(ExcludeFilter.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(IncludeFilter.class));
        scanner.scan("com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner");

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
        //ClassPathBeanDefinitionScanner 无法解析 @Import 注解
        assertFalse(beanFactory.containsBeanDefinition("importConfiguration"));
        assertFalse(beanFactory.containsBeanDefinition("bean2"));
        assertFalse(beanFactory.containsBeanDefinition("excludeFilterBean"));

        //如果装配类需要支持 @Autowired 注解类型注入 需要添加BeanPostProcessor子类实现AutowiredAnnotationBeanPostProcessor
        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = beanFactory.getBean(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME, AutowiredAnnotationBeanPostProcessor.class);
        autowiredAnnotationBeanPostProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);
        TestServiceImpl service = beanFactory.getBean("testService", TestServiceImpl.class);
        Assert.assertNotNull(service.getDao());
    }

    /**
     * ClassPathBeanDefinitionScanner 实现核心原理
     */
    @Test
    public void registerScannerBean() throws IOException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 创建注解bean名称生成器 AnnotationBeanNameGenerator
        BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

        String testServiceImplPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath("com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner") + '/' + "TestCompoment.class";
        Resource[] resources1 = resourcePatternResolver.getResources(testServiceImplPath);
        MetadataReader metadataReader = new CachingMetadataReaderFactory().getMetadataReader(resources1[0]);

        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
        sbd.setResource(resources1[0]);
        sbd.setSource(resources1[0]);
        String beanName = beanNameGenerator.generateBeanName(sbd, beanFactory);
        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(sbd, beanName);
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, beanFactory);
        assertTrue(beanFactory.containsBeanDefinition("testCompoment"));
    }
}
