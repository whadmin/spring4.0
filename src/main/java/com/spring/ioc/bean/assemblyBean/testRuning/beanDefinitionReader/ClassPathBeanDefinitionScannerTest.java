package com.spring.ioc.bean.assemblyBean.testRuning.beanDefinitionReader;

import com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner.ExcludeFilter;
import com.spring.ioc.bean.assemblyBean.beanObject.annotation.scanner.IncludeFilter;
import org.junit.Test;
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
 * 使用ClassPathBeanDefinitionScanner装配Bean
 * 1  装配注解处理器Bean
 * 2  装配ClassPath下指定包名称路径中被@Component注解及其@Component"派生性"修饰的类作为Bean装配
 *        可以通过下面方式添加自定义包含指定注解及其"派生性"注解修饰的类作为需要装配的类
 *        scanner.addIncludeFilter(new AnnotationTypeFilter(IncludeFilter.class));
 *        可以通过下面方式添加自定义排除指定注解及其"派生性"注解修饰的类作为装配的类
 *        scanner.addIncludeFilter(new AnnotationTypeFilter(IncludeFilter.class));
 *
 * @Author: wuhao.w
 * @Date: 2020/1/2 15:23
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
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_FACTORY_BEAN_NAME));

        assertTrue(beanFactory.containsBeanDefinition("testService"));
        assertTrue(beanFactory.containsBeanDefinition("testDao"));
        assertTrue(beanFactory.containsBeanDefinition("testCompoment"));
        assertTrue(beanFactory.containsBeanDefinition("testCache"));
        assertTrue(beanFactory.containsBeanDefinition("testAction"));
        assertTrue(beanFactory.containsBeanDefinition("includeFilterBean"));
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
