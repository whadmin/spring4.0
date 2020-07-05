package com.spring.ioc.bean.getBean.populateBean.testRuning;

import com.spring.BaseTest;
import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowireByTypePrimaryBean;
import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowirebyTypeBean;
import com.spring.ioc.bean.getBean.populateBean.javaConfig.BeanAutowireByType;
import com.spring.ioc.bean.getBean.populateBean.javaConfig.BeanAutowireByTypeError;
import com.spring.ioc.bean.getBean.populateBean.javaConfig.BeanAutowireByTypePrimary;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class AutowireByTypeBeanTest extends BaseTest {

    @Test
    public void testAutowireByType1() throws Exception {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/autowireByType.xml");

        AutowirebyTypeBean bean1 = beanFactory.getBean("bean_byType",
                AutowirebyTypeBean.class);
        assertThat(bean1.getDataSource()).isNotNull();
    }

    @Test
    public void testAutowireByType2() throws Exception {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 Java注解类装配Bean
        annotatedBeanAssembly(beanFactory, BeanAutowireByType.class);

        AutowirebyTypeBean bean2 = beanFactory.getBean("bean_byType",
                AutowirebyTypeBean.class);
        assertThat(bean2.getDataSource()).isNotNull();
    }


    @Test(expected = Exception.class)
    public void testAutowireByTypeError1() throws Exception {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/autowireByTypeError.xml");

        beanFactory.getBean("bean_byType_error",
                AutowirebyTypeBean.class);

    }

    @Test(expected = Exception.class)
    public void testAutowireByTypeError2() throws Exception {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 Java注解类装配Bean
        annotatedBeanAssembly(beanFactory, BeanAutowireByTypeError.class);

        beanFactory.getBean("bean_byType_error",
                AutowirebyTypeBean.class);
    }

    @Test
    public void testAutowireByTypePrimary1() throws Exception {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/getBean/populateBean/autowireByTypePrimary.xml");

        AutowireByTypePrimaryBean bean1 = beanFactory.getBean("bean_byType_primary",
                AutowireByTypePrimaryBean.class);
        assertThat(bean1.getDataSource()).isNotNull();
    }

    @Test
    public void testAutowireByTypePrimary2() throws Exception {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 Java注解类装配Bean
        annotatedBeanAssembly(beanFactory, BeanAutowireByTypePrimary.class);

        AutowireByTypePrimaryBean bean1 = beanFactory.getBean("bean_byType_primary",
                AutowireByTypePrimaryBean.class);
        assertThat(bean1.getDataSource()).isNotNull();
    }
}

