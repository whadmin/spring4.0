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
public class AutowireByAnnBeanTest extends BaseTest {


    @Test
    public void testAutowireByAnnotationFalse() throws IOException {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/autowireByAnnotationFalse.xml");

        AutowireByAnnBean bean = beanFactory.getBean("bean_annotation",
                AutowireByAnnBean.class);
        assertThat(bean.getDataSource1()).isNull();
        assertThat(bean.getDataSource2()).isNull();
    }

    @Test
    public void testAutowireByAnnotationTrue() throws IOException {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/autowireByAnnotationTrue.xml");

        AutowireByAnnBean bean1 = beanFactory.getBean("bean_annotation",
                AutowireByAnnBean.class);
        assertThat(bean1.getDataSource1()).isNotNull();
        assertThat(bean1.getDataSource2()).isNotNull();

        AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext(BeanAutowireByAnnotationTrue.class);
        com.spring.ioc.bean.getBean.populateBean.beanObject.annotation.AutowireByAnnBean bean2 = context2.getBean("bean_annotation",
                com.spring.ioc.bean.getBean.populateBean.beanObject.annotation.AutowireByAnnBean.class);
        assertThat(bean2.getDataSource1()).isNotNull();
        assertThat(bean2.getDataSource2()).isNotNull();
    }


    @Test(expected = Exception.class)
    public void testAutowireByAnnotationError() throws IOException {
        ClassPathXmlApplicationContext context1 = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/createBeanInstance/autowireByAnnotationError.xml");
        com.spring.ioc.bean.getBean.populateBean.beanObject.annotation.AutowireByAnnBean bean1 = context1.getBean("bean_annotation",
                com.spring.ioc.bean.getBean.populateBean.beanObject.annotation.AutowireByAnnBean.class);
        assertThat(bean1.getDataSource1()).isNotNull();
        assertThat(bean1.getDataSource2()).isNotNull();


        AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext(BeanAutowireByAnnotationError.class);
        com.spring.ioc.bean.getBean.populateBean.beanObject.annotation.AutowireByAnnBean bean2 = context2.getBean("bean_annotation",
                com.spring.ioc.bean.getBean.populateBean.beanObject.annotation.AutowireByAnnBean.class);
        assertThat(bean2.getDataSource1()).isNotNull();
        assertThat(bean2.getDataSource2()).isNotNull();
    }

    @Test
    public void testAutowireByQualifier() throws IOException {
        ClassPathXmlApplicationContext context1 = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/createBeanInstance/autowireByConstructorQualifierFalse.xml");
        AutowireByAnnQualifierBean bean1 = context1.getBean("bean_byType_qualifier",
                AutowireByAnnQualifierBean.class);
        assertThat(bean1.getMysqlDataSource1()).isNotNull();
        assertThat(bean1.getMysqlDataSource2()).isNotNull();
        assertThat(bean1.getMysqlDataSource3()).isNotNull();
        assertThat(bean1.getOracleDataSource1()).isNotNull();
        assertThat(bean1.getOracleDataSource2()).isNotNull();
        assertThat(bean1.getOracleDataSource3()).isNotNull();

        /** 通过@Configuration 装配的bean 在Qualifier 功能更加单一  **/
        AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext(BeanAutowireByAnnotationQualifier.class);
        AutowireByAnnQualifierBean2 bean2 = context2.getBean("bean_byType_qualifier",
                AutowireByAnnQualifierBean2.class);
        assertThat(bean2.getMysqlDataSource()).isNotNull();
        assertThat(bean2.getOracleDataSource()).isNotNull();
    }
}
