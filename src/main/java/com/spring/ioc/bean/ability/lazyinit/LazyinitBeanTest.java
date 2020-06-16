package com.spring.ioc.bean.ability.lazyinit;

import com.spring.ioc.bean.ability.scope.beanObject.no_annotation.SingletonBean;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author: wuhao.w
 * @Date: 2020/6/16 12:13
 */
public class LazyinitBeanTest {

    /**
     * 手动装配单例延迟Bean
     */
    @Test
    public void testAssemblySingleton1() throws Exception {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
                "com.spring.ioc.bean.ability.lazyinit.beanObject.no_annotation.LazyinitBean", null);
        beanDefinition1.setScope("singleton");
        //设置为非延迟加载
        beanDefinition1.setLazyInit(false);
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition1, "singletonBean", null), beanFactory);

        AbstractBeanDefinition beanDefinition2 = BeanDefinitionReaderUtils.createBeanDefinition(null,
                "com.spring.ioc.bean.ability.lazyinit.beanObject.no_annotation.LazyinitBean", null);
        beanDefinition1.setScope("singleton");
        //设置为延迟加载
        beanDefinition1.setLazyInit(true);

        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition1, "nolazyinit", null), beanFactory);
        BeanDefinitionReaderUtils.registerBeanDefinition(
                new BeanDefinitionHolder(beanDefinition2, "lazyinit", null), beanFactory);

        // 3 获取BeanDefinition
        assertThat(beanFactory.getBeanDefinition("nolazyinit")).isNotNull();
        assertThat(beanFactory.getBeanDefinition("lazyinit")).isNotNull();
    }

    /**
     * 使用 XmlBeanDefinitionReader 装配单例延迟Bean
     */
    @Test
    public void testAssemblySingleton2() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        Resource resource = new ClassPathResource("ioc/bean/ability/lazyinit/lazyinitBean.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        // 3 获取BeanDefinition
        assertThat(beanFactory.getBeanDefinition("nolazyinit")).isNotNull();
        assertThat(beanFactory.getBeanDefinition("lazyinit")).isNotNull();
    }

    /**
     * 测试单例Bean
     */
    @Test
    public void testSimpleSingleton() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        Resource resource = new ClassPathResource("ioc/bean/ability/lazyinit/lazyinitBean.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        assertThat(beanFactory.getBeanDefinition("nolazyinit")).isNotNull();
        assertThat(beanFactory.getBeanDefinition("lazyinit")).isNotNull();

        // 3 预加载单例Bean,已加载单例Bean会放到单例缓存池中(无法加载延迟单例Bean)
        beanFactory.preInstantiateSingletons();

        // 4 从单例缓存池中获取预加载单例Bean
        Object singletonBean1 = beanFactory.getSingleton("nolazyinit");
        assertThat(singletonBean1).isNotNull();

        // 5 获取单例bean,如果单例缓存池中存在，从单例缓存池中获取，不存在新创建并放入单例缓存池
        SingletonBean singletonBean2 = beanFactory.getBean("nolazyinit", SingletonBean.class);
        SingletonBean singletonBean3 = beanFactory.getBean("nolazyinit", SingletonBean.class);
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

        // 2 装配Bean
        Resource resource = new ClassPathResource("ioc/bean/ability/lazyinit/lazyinitBean.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        assertThat(beanFactory.getBeanDefinition("nolazyinit")).isNotNull();
        assertThat(beanFactory.getBeanDefinition("lazyinit")).isNotNull();

        // 3 预加载单例Bean,已加载单例Bean会放到单例缓存池中(无法加载延迟单例Bean)
        beanFactory.preInstantiateSingletons();

        // 4 singletonBean2 是延迟加载Bean 无法通过preInstantiateSingletons预加载到单例缓存池中
        Object singletonBean1 = beanFactory.getSingleton("lazyinit");
        assertThat(singletonBean1).isNull();

        // 5 获取单例bean,如果单例缓存池中存在，从单例缓存池中获取，不存在新创建并放入单例缓存池
        SingletonBean singletonBean2 = beanFactory.getBean("lazyinit", SingletonBean.class);
        SingletonBean singletonBean3 = beanFactory.getBean("lazyinit", SingletonBean.class);
        assertThat(singletonBean2).isNotNull();
        assertThat(singletonBean3).isNotNull();

        // 6 多次获取单例Bean是同一个实例
        assertThat(singletonBean2).isEqualTo(singletonBean3);

        // 7 从单例缓存池中获取预加载单例Bean
        singletonBean1 = beanFactory.getSingleton("lazyinit");

        // 8 多次换取单例Bean 来与单例缓存
        assertThat(singletonBean1).isEqualTo(singletonBean2);
        assertThat(singletonBean1).isEqualTo(singletonBean3);
    }

    @Test
    public void testLazyInitSingleton2() {
        // 1 ClassPathXmlApplicationContext 内部包含了DefaultListableBeanFactory
        // 2 ClassPathXmlApplicationContext 内部通过XmlBeanDefinitionReader 读取指定xml配置装配Bean
        // 3 ClassPathXmlApplicationContext 内部refresh()->finishBeanFactoryInitialization() ->beanFactory.preInstantiateSingletons();预处理单例Bean
        // AbstractApplicationContext.finishBeanFactoryInitialization(ConfigurableListableBeanFactory)  (org.springframework.context.support)
        //AbstractApplicationContext.refresh()  (org.springframework.context.support)
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ioc/bean/ability/lazyinit/lazyinitBean.xml");
        //ClassPathXmlApplicationContext 内部包含了DefaultListableBeanFactory
        assertThat(context.getBeanFactory() instanceof DefaultListableBeanFactory);


        Object singletonBean1 = context.getBeanFactory().getSingleton("nolazyinit");
        assertThat(singletonBean1).isNotNull();
        Object singletonBean2 = context.getBeanFactory().getSingleton("lazyinit");
        assertThat(singletonBean2).isNull();
    }
}
