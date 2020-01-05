package com.spring.ioc.bean.getBean.propertyInjection.application;

import com.spring.ioc.bean.getBean.propertyInjection.beanObject.no_annotation.AutowireByTypePrimaryBean;
import com.spring.ioc.bean.getBean.propertyInjection.beanObject.no_annotation.AutowirebyTypeBean;
import com.spring.ioc.bean.getBean.propertyInjection.javaConfig.BeanAutowireByType;
import com.spring.ioc.bean.getBean.propertyInjection.javaConfig.BeanAutowireByTypeError;
import com.spring.ioc.bean.getBean.propertyInjection.javaConfig.BeanAutowireByTypePrimary;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class AutowireByTypeBeanInject {

    @Test
    public void testAutowireByType() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/methodInjection/beanAutowireByType.xml");
        AutowirebyTypeBean bean1 = context.getBean("bean_byType",
                AutowirebyTypeBean.class);
        assertThat(bean1.getDataSource()).isNotNull();

        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext(BeanAutowireByType.class);
        AutowirebyTypeBean bean2 = context1.getBean("bean_byType",
                AutowirebyTypeBean.class);
        assertThat(bean2.getDataSource()).isNotNull();
    }

    @Test(expected = Exception.class)
    public void testAutowireByTypeError() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/methodInjection/beanAutowireByTypeError.xml");
        AutowirebyTypeBean bean1 = context.getBean("bean_byType_error",
                AutowirebyTypeBean.class);

        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext(BeanAutowireByTypeError.class);
        AutowirebyTypeBean bean2 = context1.getBean("bean_byType",
                AutowirebyTypeBean.class);
    }

    @Test
    public void testAutowireByTypePrimary() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/methodInjection/beanAutowireByTypePrimary.xml");
        AutowireByTypePrimaryBean bean1 = context.getBean("bean_byType_primary",
                AutowireByTypePrimaryBean.class);
        assertThat(bean1.getDataSource()).isNotNull();

        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext(BeanAutowireByTypePrimary.class);
        AutowirebyTypeBean bean2 = context1.getBean("bean_byType_primary",
                AutowirebyTypeBean.class);
        assertThat(bean2.getDataSource()).isNotNull();
    }
}

