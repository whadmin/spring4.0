package com.spring.ioc.bean.ability.scope;

import com.spring.BaseTest;
import com.spring.ioc.bean.ability.scope.beanObject.no_annotation.SingletonBean;
import com.spring.ioc.bean.ability.scope.javaConfig.SingletonBeanConfig;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author: wuhao.w
 * @Date: 2020/6/16 10:20
 */
public class SingletonBeanTest extends BaseTest {


    /**
     * 手动装配一个单例Bean
     */
    @Test
    public void testAssemblySingleton1() throws Exception {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
                "com.spring.ioc.bean.ability.scope.beanObject.no_annotation.SingletonBean", null);
        beanDefinition1.setScope("singleton");
        beanDefinition1.setLazyInit(false);
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition1, "singletonBean", null), beanFactory);

        AbstractBeanDefinition beanDefinition2 = BeanDefinitionReaderUtils.createBeanDefinition(null,
                "com.spring.ioc.bean.ability.scope.beanObject.no_annotation.SingletonBean", null);
        beanDefinition1.setScope("singleton");
        beanDefinition1.setLazyInit(true);

        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition1, "singletonBean", null), beanFactory);
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition1, "singletonBean2", null), beanFactory);

        // 3 获取BeanDefinition
        assertThat(beanFactory.getBeanDefinition("singletonBean")).isNotNull();
        assertThat(beanFactory.getBeanDefinition("singletonBean2")).isNotNull();
    }

    /**
     * xml配置文件装配一个单例Bean
     */
    @Test
    public void testAssemblySingleton2() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 xml配置文件装配Bean
        xmlAssembly(beanFactory, "ioc/bean/ability/scope/singletonBean.xml");

        // 3 获取BeanDefinition
        assertThat(beanFactory.getBeanDefinition("singletonBean")).isNotNull();
        assertThat(beanFactory.getBeanDefinition("singletonBean2")).isNotNull();
    }


    /**
     * Java注解类装配Bean一个原型Bean
     */
    @Test
    public void testAssemblySingleton3() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 Java注解类装配Bean
        annotatedBeanAssembly(beanFactory, SingletonBeanConfig.class);

        // 3 获取BeanDefinition
        assertThat(beanFactory.getBeanDefinition("singletonBean")).isNotNull();
        assertThat(beanFactory.getBeanDefinition("singletonBean2")).isNotNull();
    }

    /**
     * 测试单例Bean
     */
    @Test
    public void testSimpleSingleton() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 xml配置文件装配Bean
        xmlAssembly(beanFactory, "ioc/bean/ability/scope/singletonBean.xml");
        // 2 Java注解类装配Bean
        //annotatedBeanAssembly(beanFactory, SingletonBeanConfig.class);

        // 3 预加载单例Bean,已加载单例Bean会放到单例对象池中(无法加载延迟单例Bean)
        beanFactory.preInstantiateSingletons();

        // 4 从单例缓存池中获取预加载单例Bean
        Object singletonBean1 = beanFactory.getSingleton("singletonBean");
        assertThat(singletonBean1).isNotNull();

        // 5 获取单例bean,如果单例缓存池中存在，从单例缓存池中获取，不存在新创建并放入单例缓存池
        SingletonBean singletonBean2 = beanFactory.getBean("singletonBean", SingletonBean.class);
        SingletonBean singletonBean3 = beanFactory.getBean("singletonBean", SingletonBean.class);
        assertThat(singletonBean2).isNotNull();
        assertThat(singletonBean3).isNotNull();

        // 6 多次获取单例Bean是同一个实例
        assertThat(singletonBean2).isEqualTo(singletonBean3);

        // 7 多次换取单例Bean 来与单例缓存
        assertThat(singletonBean1).isEqualTo(singletonBean2);
        assertThat(singletonBean1).isEqualTo(singletonBean3);
    }


    /**
     * 测试设置延迟加载单例Bean
     */
    @Test
    public void testLazyInitSingleton() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 xml配置文件装配Bean
        xmlAssembly(beanFactory, "ioc/bean/ability/scope/singletonBean.xml");
        // 2 Java注解类装配Bean
        //annotatedBeanAssembly(beanFactory, SingletonBeanConfig.class);

        assertThat(beanFactory.getBeanDefinition("singletonBean")).isNotNull();
        assertThat(beanFactory.getBeanDefinition("singletonBean2")).isNotNull();

        // 3 预加载单例Bean,已加载单例Bean会放到单例对象池中(无法加载延迟单例Bean)
        beanFactory.preInstantiateSingletons();

        // 4 singletonBean2 是延迟加载Bean 无法通过preInstantiateSingletons预加载到单例缓存池中
        Object singletonBean1 = beanFactory.getSingleton("singletonBean2");
        assertThat(singletonBean1).isNull();

        // 5 获取单例bean,如果单例缓存池中存在，从单例缓存池中获取，不存在新创建并放入单例缓存池
        SingletonBean singletonBean2 = beanFactory.getBean("singletonBean2", SingletonBean.class);
        SingletonBean singletonBean3 = beanFactory.getBean("singletonBean2", SingletonBean.class);
        assertThat(singletonBean2).isNotNull();
        assertThat(singletonBean3).isNotNull();

        // 6 多次获取单例Bean是同一个实例
        assertThat(singletonBean2).isEqualTo(singletonBean3);

        // 7 从单例缓存池中获取预加载单例Bean
        singletonBean1 = beanFactory.getSingleton("singletonBean2");

        // 8 多次换取单例Bean 来与单例缓存
        assertThat(singletonBean1).isEqualTo(singletonBean2);
        assertThat(singletonBean1).isEqualTo(singletonBean3);
    }
}
