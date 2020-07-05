package com.spring.ioc.bean.getBean.populateBean.testRuning.core;


import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowirebyTypeBean;
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

public class AutowireByTypeBeanInjectCore {

    /**
     * 属性类型自动注入
     * @throws ClassNotFoundException
     */
    @Test
    public  void testInstantiatingBeanByType() throws ClassNotFoundException {
        // 1 定义BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 定义beanDefinition
        AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null,
                "com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowirebyTypeBean", null);
        beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

        // 3 注册到 BeanFactory
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition, "bean_byType_at", null), beanFactory);

        BeanDefinition bean_byType_at = beanFactory.getBeanDefinition("bean_byType_at");
        System.err.println(ReflectionToStringBuilder.toString(bean_byType_at, ToStringStyle.MULTI_LINE_STYLE));

        Resource resource = new ClassPathResource("ioc/bean/getBean/populateBean/autowireByType.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        BeanDefinition bean_byType = beanFactory.getBeanDefinition("bean_byType");
        System.err.println(ReflectionToStringBuilder.toString(bean_byType, ToStringStyle.MULTI_LINE_STYLE));

        AutowirebyTypeBean bean1 = beanFactory.getBean("bean_byType", AutowirebyTypeBean.class);
        assertThat(bean1.getDataSource()).isNotNull();

        AutowirebyTypeBean bean2 = beanFactory.getBean("bean_byType_at", AutowirebyTypeBean.class);
        assertThat(bean2.getDataSource()).isNotNull();
    }
}

