package com.spring.ioc.bean.getBean.populateBean.application.core;

import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowireByNameBean;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;


public class AutowireByNameBeanInjectCore {

    /**
     * 属性名称自动注入
     * @throws ClassNotFoundException
     */
    @Test
    public void testInstantiatingBeanByName() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null,
                "com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowireByNameBean", null);
        beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils
                .registerBeanDefinition(new BeanDefinitionHolder(beanDefinition, "bean_byName_at", null), beanFactory);

        BeanDefinition bean_byName_at = beanFactory.getBeanDefinition("bean_byName_at");
        System.err.println(ReflectionToStringBuilder.toString(bean_byName_at, ToStringStyle.MULTI_LINE_STYLE));


        Resource resource = new ClassPathResource("ioc/bean/getBean/populateBean/autowireByName.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        BeanDefinition bean_byName = beanFactory.getBeanDefinition("bean_byName");
        System.err.println(ReflectionToStringBuilder.toString(bean_byName, ToStringStyle.MULTI_LINE_STYLE));

        AutowireByNameBean bean1 = beanFactory.getBean("bean_byName", AutowireByNameBean.class);
        assertThat(bean1.getMysqlDataSource()).isNotNull();
        assertThat(bean1.getOracleDataSource()).isNotNull();

        AutowireByNameBean bean2 = beanFactory.getBean("bean_byName_at", AutowireByNameBean.class);
        assertThat(bean2.getMysqlDataSource()).isNotNull();
        assertThat(bean2.getOracleDataSource()).isNotNull();
    }
}
