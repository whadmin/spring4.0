package com.spring.ioc.bean.getBean.propertyInjection.application;

import java.io.IOException;

import com.spring.ioc.bean.getBean.propertyInjection.beanObject.no_annotation.AutowireByNameBean;
import com.spring.ioc.bean.getBean.propertyInjection.javaConfig.BeanAutowireByName;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;


public class AutowireByNameBeanInject {

    @Test
    public void testAutowireByName() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/methodInjection/beanAutowireByName.xml");
        AutowireByNameBean bean1 = context.getBean("bean_byName",
                AutowireByNameBean.class);
        assertThat(bean1.getMysqlDataSource()).isNotNull();
        assertThat(bean1.getOracleDataSource()).isNotNull();

        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext(BeanAutowireByName.class);
        AutowireByNameBean bean2 = context1.getBean("bean_byName",
                AutowireByNameBean.class);
        assertThat(bean2.getMysqlDataSource()).isNotNull();
        assertThat(bean2.getOracleDataSource()).isNotNull();
    }
}