package com.spring.ioc.bean.assemblyBean.testRuning.configurationClassPostProcessor;

import com.spring.ioc.bean.assemblyBean.javaConfig.*;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * ConfigurationClassPostProcessor
 * <p>
 * 负责解析注解配置类（被@Configuration注解或派送注解修饰的类）【当前类必须已经被装配到IOC容器中】
 * 将注解配置类中配置Bean装配到IOC容器中，其中包括：
 * 1  解析类中@Bean注解配置：将方法返回作为Bean类型，方法执行作为Bean实例化构造器，将类中构作此Bean的工厂类
 * 2  解析类中@ComponentScan注解配置，及其派生注解：内部使用ClassPathBeanDefinitionScanner装配
 * 3  解析类中@Import注解，及其派生注解配置：
 * 导入一个注解类
 * 导入一个实现ImportSelector接口类，自定义重写
 *
 * @Author: wuhao.w
 * @Date: 2020/6/5 22:34
 */
public class ConfigurationAnnotationProcessorTest {

    /**
     * 解析类中@Bean注解配置：将方法返回作为Bean类型，方法执行作为Bean实例化构造器，将类中构作此Bean的工厂类
     */
    @Test
    public void useConfigurationClassAnnBeanConfig() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //使用AnnotatedBeanDefinitionReader
        //装配注解处理器Bean
        //装配指定注解配置类（这里必须被@Configuration注解或派送注解修饰）
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(ConfigurationClassAnnBeanConfig1.class);

        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_FACTORY_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition("configurationClassAnnBeanConfig1"));
        //AnnotatedBeanDefinitionReader 无法解析 @Bean 注解
        assertFalse(beanFactory.containsBeanDefinition("bean"));

        //使用ConfigurationClassPostProcessor
        //从已装配Bean中去读取被@Configuration注解或派送注解修饰的类作为注解配置类
        //对注解配置类中Bean实现装配
        ConfigurationClassPostProcessor configurationClassPostProcessor = beanFactory.getBean(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, ConfigurationClassPostProcessor.class);
        configurationClassPostProcessor.postProcessBeanFactory(beanFactory);

        //使用ConfigurationClassPostProcessor
        //解析类中@Bean注解配置
        assertTrue(beanFactory.containsBeanDefinition("bean1"));
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("bean1");
        assertTrue(beanDefinition.getFactoryBeanName().equals("configurationClassAnnBeanConfig1"));
        assertTrue(beanDefinition.getFactoryMethodName().equals("beanObject"));
    }

    /**
     * 解析类上@ComponentScan注解配置
     */
    @Test
    public void useConfigurationClassAnnComponentScanConfig() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //使用AnnotatedBeanDefinitionReader
        //装配注解处理器Bean
        //装配指定注解配置类（这里必须被@Configuration注解或派送注解修饰）
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(ConfigurationClassAnnComponentScanConfig.class);

        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_PROCESSOR_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_FACTORY_BEAN_NAME));
        assertTrue(beanFactory.containsBeanDefinition("configurationClassAnnComponentScanConfig"));
        //AnnotatedBeanDefinitionReader 无法解析 @ComponentScan注解配置
        assertFalse(beanFactory.containsBeanDefinition("testService"));
        assertFalse(beanFactory.containsBeanDefinition("testDao"));
        assertFalse(beanFactory.containsBeanDefinition("testCompoment"));
        assertFalse(beanFactory.containsBeanDefinition("testCache"));
        assertFalse(beanFactory.containsBeanDefinition("testAction"));
        assertFalse(beanFactory.containsBeanDefinition("includeFilterBean"));


        //使用ConfigurationClassPostProcessor
        //从已装配Bean中去读取被@Configuration注解或派送注解修饰的类作为注解配置类
        //对注解配置类中Bean实现装配
        ConfigurationClassPostProcessor configurationClassPostProcessor = beanFactory.getBean(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, ConfigurationClassPostProcessor.class);
        configurationClassPostProcessor.postProcessBeanFactory(beanFactory);

        //使用ConfigurationClassPostProcessor
        //解析类中@Bean注解配置
        assertTrue(beanFactory.containsBeanDefinition("testService"));
        assertTrue(beanFactory.containsBeanDefinition("testDao"));
        assertTrue(beanFactory.containsBeanDefinition("testCompoment"));
        assertTrue(beanFactory.containsBeanDefinition("testCache"));
        assertTrue(beanFactory.containsBeanDefinition("testAction"));
        assertTrue(beanFactory.containsBeanDefinition("includeFilterBean"));
    }

    @Test
    public void useConfigurationClassAnnImportConfig1() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //使用AnnotatedBeanDefinitionReader
        //装配注解处理器Bean
        //装配指定注解配置类（这里必须被@Configuration注解或派送注解修饰）
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(ConfigurationClassAnnImportConfig1.class);
        assertTrue(beanFactory.containsBeanDefinition("configurationClassAnnImportConfig1"));

        //AnnotatedBeanDefinitionReader 无法解析 @CImport注解配置
        assertFalse(beanFactory.containsBeanDefinition("configurationClassAnnBeanConfig2"));
        assertFalse(beanFactory.containsBeanDefinition("bean2"));

        //使用ConfigurationClassPostProcessor
        //从已装配Bean中去读取被@Configuration注解或派送注解修饰的类作为注解配置类
        //对注解配置类中Bean实现装配
        ConfigurationClassPostProcessor configurationClassPostProcessor = beanFactory.getBean(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, ConfigurationClassPostProcessor.class);
        configurationClassPostProcessor.postProcessBeanFactory(beanFactory);

        assertTrue(beanFactory.containsBeanDefinition("configurationClassAnnBeanConfig2"));
        assertTrue(beanFactory.containsBeanDefinition("bean2"));
    }


    @Test
    public void useConfigurationClassAnnImportConfig2() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //使用AnnotatedBeanDefinitionReader
        //装配注解处理器Bean
        //装配指定注解配置类（这里必须被@Configuration注解或派送注解修饰）
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(ConfigurationClassAnnImportConfig2.class);
        assertTrue(beanFactory.containsBeanDefinition("configurationClassAnnImportConfig2"));

        //AnnotatedBeanDefinitionReader 无法解析 @CImport注解配置
        assertFalse(beanFactory.containsBeanDefinition("configurationClassAnnBeanConfig1"));
        assertFalse(beanFactory.containsBeanDefinition("bean1"));

        //使用ConfigurationClassPostProcessor
        //从已装配Bean中去读取被@Configuration注解或派送注解修饰的类作为注解配置类
        //对注解配置类中Bean实现装配
        ConfigurationClassPostProcessor configurationClassPostProcessor = beanFactory.getBean(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, ConfigurationClassPostProcessor.class);
        configurationClassPostProcessor.postProcessBeanFactory(beanFactory);

        assertTrue(beanFactory.containsBeanDefinition("configurationClassAnnBeanConfig1"));
        assertTrue(beanFactory.containsBeanDefinition("bean1"));
    }


    @Test
    public void useConfigurationClassAnnImportConfig3() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //使用AnnotatedBeanDefinitionReader
        //装配注解处理器Bean
        //装配指定注解配置类（这里必须被@Configuration注解或派送注解修饰）
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(ConfigurationClassAnnImportConfig3.class);
        assertTrue(beanFactory.containsBeanDefinition("configurationClassAnnImportConfig3"));

        //AnnotatedBeanDefinitionReader 无法解析 @CImport注解配置
        assertFalse(beanFactory.containsBeanDefinition("configurationClassAnnBeanConfig1"));
        assertFalse(beanFactory.containsBeanDefinition("bean1"));
        assertFalse(beanFactory.containsBeanDefinition("configurationClassAnnBeanConfig2"));
        assertFalse(beanFactory.containsBeanDefinition("bean2"));

        //使用ConfigurationClassPostProcessor
        //从已装配Bean中去读取被@Configuration注解或派送注解修饰的类作为注解配置类
        //对注解配置类中Bean实现装配
        ConfigurationClassPostProcessor configurationClassPostProcessor = beanFactory.getBean(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, ConfigurationClassPostProcessor.class);
        configurationClassPostProcessor.postProcessBeanFactory(beanFactory);

        assertTrue(beanFactory.containsBeanDefinition("configurationClassAnnBeanConfig1"));
        assertTrue(beanFactory.containsBeanDefinition("bean1"));
        assertTrue(beanFactory.containsBeanDefinition("configurationClassAnnBeanConfig2"));
        assertTrue(beanFactory.containsBeanDefinition("bean2"));
    }


    @Test
    public void useConfigurationMultipleConfig() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //使用AnnotatedBeanDefinitionReader
        //装配注解处理器Bean
        //装配指定注解配置类（这里必须被@Configuration注解或派送注解修饰）
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(ConfigurationClassAnnBeanConfig3.class);
        reader.register(ConfigurationClassAnnBeanConfig1.class);
        reader.register(ConfigurationClassAnnBeanConfig2.class);
        assertFalse(beanFactory.containsBeanDefinition("bean1"));
        assertFalse(beanFactory.containsBeanDefinition("bean2"));
        assertFalse(beanFactory.containsBeanDefinition("bean3"));

        ConfigurationClassPostProcessor configurationClassPostProcessor = beanFactory.getBean(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, ConfigurationClassPostProcessor.class);
        configurationClassPostProcessor.postProcessBeanFactory(beanFactory);

        assertTrue(beanFactory.containsBeanDefinition("bean1"));
        assertTrue(beanFactory.containsBeanDefinition("bean2"));
        assertTrue(beanFactory.containsBeanDefinition("bean3"));

        Object bean3 = beanFactory.getBean("bean3");
    }
}
