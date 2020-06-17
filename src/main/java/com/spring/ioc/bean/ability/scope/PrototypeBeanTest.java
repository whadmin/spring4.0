package com.spring.ioc.bean.ability.scope;

import com.spring.BaseTest;
import com.spring.ioc.bean.ability.scope.beanObject.no_annotation.PrototypeBean;
import com.spring.ioc.bean.ability.scope.javaConfig.PrototypeBeanConfig;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;


import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeBeanTest extends BaseTest {


    /**
     * 手动装配一个原型Bean
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
     * xml配置文件装配一个原型Bean
     */
    @Test
    public void testAssemblyPrototype2() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 xml配置文件装配Bean
        xmlAssembly(beanFactory, "ioc/bean/ability/scope/prototypeBean.xml");
        // 3 获取BeanDefinition
        assertThat(beanFactory.getBeanDefinition("prototypeBean")).isNotNull();
    }


    /**
     * Java注解类装配Bean一个原型Bean
     */
    @Test
    public void testAssemblyPrototype3() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 Java注解类装配Bean
        annotatedBeanAssembly(beanFactory, PrototypeBeanConfig.class);

        // 3 获取BeanDefinition
        assertThat(beanFactory.getBeanDefinition("prototypeBean")).isNotNull();
    }


    @Test
    public void testPrototype() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2 xml配置文件装配Bean
        //xmlAssembly(beanFactory, "ioc/bean/ability/scope/prototypeBean.xml");
        // 2 Java注解类装配Bean
        annotatedBeanAssembly(beanFactory,PrototypeBeanConfig.class);

        PrototypeBean singletonBean1 = beanFactory.getBean("prototypeBean", PrototypeBean.class);
        PrototypeBean singletonBean2 = beanFactory.getBean("prototypeBean", PrototypeBean.class);
        assertThat(singletonBean1 == (singletonBean2)).isFalse();
    }
}
