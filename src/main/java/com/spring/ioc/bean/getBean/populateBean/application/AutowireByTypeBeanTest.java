package com.spring.ioc.bean.getBean.populateBean.application;

import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowireByTypePrimaryBean;
import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowirebyTypeBean;
import com.spring.ioc.bean.getBean.populateBean.javaConfig.BeanAutowireByType;
import com.spring.ioc.bean.getBean.populateBean.javaConfig.BeanAutowireByTypeError;
import com.spring.ioc.bean.getBean.populateBean.javaConfig.BeanAutowireByTypePrimary;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class AutowireByTypeBeanTest {

    @Test
    public void testAutowireByType() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/populateBean/autowireByType.xml");
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
                "ioc/bean/getBean/populateBean/autowireByTypeError.xml");
        AutowirebyTypeBean bean1 = context.getBean("bean_byType_error",
                AutowirebyTypeBean.class);

        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext(BeanAutowireByTypeError.class);
        AutowirebyTypeBean bean2 = context1.getBean("bean_byType",
                AutowirebyTypeBean.class);
    }

    @Test
    public void testAutowireByTypePrimary() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/populateBean/autowireByTypePrimary.xml");
        AutowireByTypePrimaryBean bean1 = context.getBean("bean_byType_primary",
                AutowireByTypePrimaryBean.class);
        assertThat(bean1.getDataSource()).isNotNull();

        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext(BeanAutowireByTypePrimary.class);
        AutowirebyTypeBean bean2 = context1.getBean("bean_byType_primary",
                AutowirebyTypeBean.class);
        assertThat(bean2.getDataSource()).isNotNull();
    }
}

