package com.spring.ioc.bean.getBean.populateBean.testRuning;

import java.io.IOException;

import com.spring.ioc.bean.getBean.populateBean.javaConfig.BeanAutowireByName;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;


public class AutowireByNameBeanTest {

    @Test
    public void testAutowireByName() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/bean/getBean/populateBean/autowireByName.xml");
        com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowireByNameBean bean1 = context.getBean("bean_byName",
                com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowireByNameBean.class);
        assertThat(bean1.getMysqlDataSource()).isNotNull();
        assertThat(bean1.getOracleDataSource()).isNotNull();

        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext(BeanAutowireByName.class);
        com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowireByNameBean bean2 = context1.getBean("bean_byName",
                com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowireByNameBean.class);
        assertThat(bean2.getMysqlDataSource()).isNotNull();
        assertThat(bean2.getOracleDataSource()).isNotNull();
    }
}
