package com.spring.ioc.bean.getBean.propertyInjection.application;

import com.spring.ioc.bean.getBean.propertyInjection.beanObject.annotation.AutowireByAnnBean;
import com.spring.ioc.bean.getBean.propertyInjection.beanObject.annotation.AutowireByAnnQualifierBean;
import com.spring.ioc.bean.getBean.propertyInjection.beanObject.annotation.AutowireByAnnQualifierBean2;
import com.spring.ioc.bean.getBean.propertyInjection.javaConfig.BeanAutowireByAnnotationError;
import com.spring.ioc.bean.getBean.propertyInjection.javaConfig.BeanAutowireByAnnotationQualifier;
import com.spring.ioc.bean.getBean.propertyInjection.javaConfig.BeanAutowireByAnnotationTrue;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 20:29
 */
public class AutowireByAnnInject {

    public void registerAnnotationConfigProcessors(ApplicationContext context){
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME)).isTrue();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)).isTrue();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME)).isTrue();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME)).isTrue();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_PROCESSOR_BEAN_NAME)).isTrue();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_FACTORY_BEAN_NAME)).isTrue();
    }

    public void unregisterAnnotationConfigProcessors(ApplicationContext context){
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME)).isFalse();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)).isFalse();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME)).isFalse();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME)).isFalse();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_PROCESSOR_BEAN_NAME)).isFalse();
        assertThat(context.containsBeanDefinition(AnnotationConfigUtils.EVENT_LISTENER_FACTORY_BEAN_NAME)).isFalse();
    }


    /**
     * @throws IOException
     */
    @Test
    public void testAutowireByAnnotationFalse() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/injection/beanAutowireByAnnotationFalse.xml");
        AutowireByAnnBean bean = context.getBean("bean_annotation",
                AutowireByAnnBean.class);
        assertThat(bean.getMysqlDataSource1()).isNull();
        assertThat(bean.getMysqlDataSource2()).isNull();
        unregisterAnnotationConfigProcessors(context);
    }

    @Test
    public void testAutowireByAnnotationTrue() throws IOException {
        ClassPathXmlApplicationContext context1 = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/injection/beanAutowireByAnnotationTrue.xml");
        AutowireByAnnBean bean1 = context1.getBean("bean_annotation",
                AutowireByAnnBean.class);
        assertThat(bean1.getMysqlDataSource1()).isNotNull();
        assertThat(bean1.getMysqlDataSource2()).isNotNull();
        registerAnnotationConfigProcessors(context1);

        AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext(BeanAutowireByAnnotationTrue.class);
        AutowireByAnnBean bean2 = context2.getBean("bean_annotation",
                AutowireByAnnBean.class);
        assertThat(bean2.getMysqlDataSource1()).isNotNull();
        assertThat(bean2.getMysqlDataSource2()).isNotNull();
        registerAnnotationConfigProcessors(context2);
    }


    @Test(expected = Exception.class)
    public void testAutowireByAnnotationError() throws IOException {
        ClassPathXmlApplicationContext context1 = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/injection/beanAutowireByAnnotationError.xml");
        AutowireByAnnBean bean1 = context1.getBean("bean_annotation",
                AutowireByAnnBean.class);
        assertThat(bean1.getMysqlDataSource1()).isNotNull();
        assertThat(bean1.getMysqlDataSource2()).isNotNull();
        registerAnnotationConfigProcessors(context1);


        AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext(BeanAutowireByAnnotationError.class);
        AutowireByAnnBean bean2 = context2.getBean("bean_annotation",
                AutowireByAnnBean.class);
        assertThat(bean2.getMysqlDataSource1()).isNotNull();
        assertThat(bean2.getMysqlDataSource2()).isNotNull();
        registerAnnotationConfigProcessors(context2);
    }

    @Test
    public void testAutowireByQualifier() throws IOException {
        ClassPathXmlApplicationContext context1 = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/injection/beanAutowireByAnnotationQualifier.xml");
        AutowireByAnnQualifierBean bean1 = context1.getBean("bean_byType_qualifier",
                AutowireByAnnQualifierBean.class);
        assertThat(bean1.getMysqlDataSource1()).isNotNull();
        assertThat(bean1.getMysqlDataSource2()).isNotNull();
        assertThat(bean1.getMysqlDataSource3()).isNotNull();
        assertThat(bean1.getOracleDataSource1()).isNotNull();
        assertThat(bean1.getOracleDataSource2()).isNotNull();
        assertThat(bean1.getOracleDataSource3()).isNotNull();
        registerAnnotationConfigProcessors(context1);

        /** 通过@Configuration 装配的bean 在Qualifier 功能更加单一  **/
        AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext(BeanAutowireByAnnotationQualifier.class);
        AutowireByAnnQualifierBean2 bean2 = context2.getBean("bean_byType_qualifier",
                AutowireByAnnQualifierBean2.class);
        assertThat(bean2.getMysqlDataSource()).isNotNull();
        assertThat(bean2.getOracleDataSource()).isNotNull();
        registerAnnotationConfigProcessors(context2);
    }
}
