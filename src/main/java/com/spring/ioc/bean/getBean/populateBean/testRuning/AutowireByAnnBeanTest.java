package com.spring.ioc.bean.getBean.populateBean.testRuning;

import com.spring.BaseTest;
import com.spring.ioc.bean.getBean.populateBean.beanObject.annotation.AutowireByAnnBean;
import com.spring.ioc.bean.getBean.populateBean.beanObject.annotation.AutowireByAnnQualifierBean;
import com.spring.ioc.bean.getBean.populateBean.beanObject.annotation.AutowireByAnnQualifierBean2;
import com.spring.ioc.bean.getBean.populateBean.javaConfig.BeanAutowireByAnnotationError;
import com.spring.ioc.bean.getBean.populateBean.javaConfig.BeanAutowireByAnnotationQualifier;
import com.spring.ioc.bean.getBean.populateBean.javaConfig.BeanAutowireByAnnotationTrue;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 20:29
 */
public class AutowireByAnnBeanTest extends BaseTest {


    /**
     * 使用注解注入，必须注册装配注解处理器
     * AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME
     * AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME
     * <p>
     * 1 xml配置文件装配Bean时,必须添加<context:annotation-config/>配置，这样IOC会自动装配
     * AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME, AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME
     * <p>
     * 2 获取装配 AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME, AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME
     * 实例添加到IOC中作为BeanPostProcessor
     * beanFactory.addBeanPostProcessor(commonAnnotationBeanPostProcessor);
     * beanFactory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);
     */
    @Test
    public void testAutowireByAnnotationXMLConfig() throws IOException {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/autowireByAnnotationFalse.xml");

        AutowireByAnnBean bean = beanFactory.getBean("bean_annotation",
                AutowireByAnnBean.class);
        assertThat(bean.getDataSource1()).isNull();
        assertThat(bean.getDataSource2()).isNull();

        // 1.初始化容器
        DefaultListableBeanFactory beanFactory1 = new DefaultListableBeanFactory();
        // 2 装配Bean
        xmlAssembly(beanFactory1, "ioc/bean/getBean/populateBean/autowireByAnnotationTrue.xml");

        AutowireByAnnBean bean1 = beanFactory1.getBean("bean_annotation",
                AutowireByAnnBean.class);
        assertThat(bean1.getDataSource1()).isNotNull();
        assertThat(bean1.getDataSource2()).isNotNull();
    }


    /**
     * 使用注解自动自动，依赖存在多个相同类型时报错
     */
    @Test(expected = Exception.class)
    public void testAutowireByAnnotationMultipleMatchesErrorXMLConfig() throws IOException {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/autowireByAnnotationError.xml");

        AutowireByAnnBean bean1 = beanFactory.getBean("bean_annotation",
                AutowireByAnnBean.class);
        assertThat(bean1.getDataSource1()).isNotNull();
        assertThat(bean1.getDataSource2()).isNotNull();
    }

    /**
     * 使用注解自动自动，依赖存在多个相同类型时报错,使用@Qualifier注解
     * <p>
     * 使用Qualifier功能
     * 1 xml配置<context:annotation-config/>XmlBeanDefinitionReader会读取装配设置自动装配器ContextAnnotationAutowireCandidateResolver
     */
    @Test
    public void testAutowireByAnnotationMultipleMatchesUseQualifierXMLConfig1() throws IOException {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/autowireByAnnotationQualifierTrue.xml");

        assertThat(beanFactory.getAutowireCandidateResolver() instanceof ContextAnnotationAutowireCandidateResolver);

        AutowireByAnnQualifierBean bean1 = beanFactory.getBean("bean_byType_qualifier",
                AutowireByAnnQualifierBean.class);
        assertThat(bean1.getMysqlDataSource1()).isNotNull();
        assertThat(bean1.getMysqlDataSource2()).isNotNull();
        assertThat(bean1.getMysqlDataSource3()).isNotNull();
        assertThat(bean1.getOracleDataSource1()).isNotNull();
        assertThat(bean1.getOracleDataSource2()).isNotNull();
        assertThat(bean1.getOracleDataSource3()).isNotNull();
    }



    /**
     * 使用注解注入，必须注册装配注解处理器
     * AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME
     * AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME
     * <p>
     * 1 java配置中AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME会自会自动装配
     * AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME, AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME
     * <p>
     * 2 获取装配 AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME, AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME
     * 实例添加到IOC中作为BeanPostProcessor
     * beanFactory.addBeanPostProcessor(commonAnnotationBeanPostProcessor);
     * beanFactory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);
     */
    @Test
    public void testAutowireByAnnotationJavaConfig() throws IOException {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 Java注解类装配Bean
        annotatedBeanAssembly(beanFactory, BeanAutowireByAnnotationTrue.class);

        AutowireByAnnBean bean2 = beanFactory.getBean("bean_annotation",
                AutowireByAnnBean.class);
        assertThat(bean2.getDataSource1()).isNotNull();
        assertThat(bean2.getDataSource2()).isNotNull();
    }


    /**
     * 使用注解自动自动，依赖存在多个相同类型时报错
     */
    @Test(expected = Exception.class)
    public void testAutowireByAnnotationMultipleMatchesErrorJavaConfig() throws IOException {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 Java注解类装配Bean
        annotatedBeanAssembly(beanFactory, BeanAutowireByAnnotationError.class);

        AutowireByAnnBean bean1 = beanFactory.getBean("bean_annotation",
                AutowireByAnnBean.class);
        assertThat(bean1.getDataSource1()).isNotNull();
        assertThat(bean1.getDataSource2()).isNotNull();
    }


    /**
     * 使用注解自动自动，依赖存在多个相同类型时报错,使用@Qualifier注解
     * <p>
     */
    @Test
    public void testAutowireByQualifier() throws IOException {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 Java注解类装配Bean
        annotatedBeanAssembly(beanFactory, BeanAutowireByAnnotationQualifier.class);

        /** 通过@Configuration 装配的bean 在Qualifier 功能更加单一  **/
        AutowireByAnnQualifierBean2 bean1 = beanFactory.getBean("bean_byType_qualifier",
                AutowireByAnnQualifierBean2.class);
        assertThat(bean1.getMysqlDataSource()).isNotNull();
        assertThat(bean1.getOracleDataSource()).isNotNull();
    }
}
