package com.spring.ioc.bean.ability.scope;

import com.spring.ioc.bean.ability.scope.beanObject.no_annotation.SingletonBean;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeBeanTest {


    /**
     * 手动装配一个单例Bean
     */
    @Test
    public void testAssemblyPrototype1() throws Exception {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
                "com.spring.ioc.bean.ability.scope.beanObject.no_annotation.PrototypeBean", null);
        beanDefinition1.setScope("prototype");
        beanDefinition1.setLazyInit(false);
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition1, "prototypeBean", null), beanFactory);

        // 3 获取BeanDefinition
        assertThat(beanFactory.getBeanDefinition("prototypeBean")).isNotNull();
    }

    /**
     * XmlBeanDefinitionReader 装配一个单例Bean
     */
    @Test
    public void testAssemblyPrototype2() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        Resource resource = new ClassPathResource("ioc/bean/ability/scope/prototypeBean.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        // 3 获取BeanDefinition
        assertThat(beanFactory.getBeanDefinition("prototypeBean")).isNotNull();
    }


    @Test
    public void testPrototype() throws Exception {
        // 2.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        Resource resource = new ClassPathResource("ioc/bean/ability/scope/prototypeBean.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        SingletonBean singletonBean1 = beanFactory.getBean("prototypeBean", SingletonBean.class);
        SingletonBean singletonBean2 = beanFactory.getBean("prototypeBean", SingletonBean.class);
        System.out.println(singletonBean1!=singletonBean2);

    }
}
