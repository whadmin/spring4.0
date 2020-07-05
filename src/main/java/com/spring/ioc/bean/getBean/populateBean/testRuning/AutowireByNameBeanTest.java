package com.spring.ioc.bean.getBean.populateBean.testRuning;

import java.io.IOException;

import com.spring.BaseTest;
import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowireByNameBean;
import com.spring.ioc.bean.getBean.populateBean.javaConfig.BeanAutowireByName;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import static org.assertj.core.api.Assertions.assertThat;


public class AutowireByNameBeanTest extends BaseTest {

    @Test
    public void testAutowireByName1() throws IOException {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/autowireByName.xml");

        AutowireByNameBean bean1 = beanFactory.getBean("bean_byName",
                AutowireByNameBean.class);
        assertThat(bean1.getMysqlDataSource()).isNotNull();
        assertThat(bean1.getOracleDataSource()).isNotNull();
    }


    @Test
    public void testAutowireByName2() throws IOException {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 Java注解类装配Bean
        annotatedBeanAssembly(beanFactory, BeanAutowireByName.class);

        AutowireByNameBean bean1 = beanFactory.getBean("bean_byName",
                AutowireByNameBean.class);
        assertThat(bean1.getMysqlDataSource()).isNotNull();
        assertThat(bean1.getOracleDataSource()).isNotNull();
    }
}
