package com.spring.ioc.bean.specialbean.factoryBean;

import com.spring.BaseTest;
import com.spring.ioc.bean.specialbean.factoryBean.beanObject.CarBean;
import com.spring.ioc.bean.specialbean.factoryBean.beanObject.CarFactoryBean;
import com.spring.ioc.bean.specialbean.factoryBean.beanObject.SmartCartFactoryBean;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;

public class FactoryBeanTest extends BaseTest {


    @Test
    public void testAssemblyFactoryBean() throws Exception {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/specialbean/factoryBean.xml");

        // 3 获取BeanDefinition
        assertThat(beanFactory.getBeanDefinition("carBean")).isNotNull();
        assertThat(beanFactory.getBeanDefinition("carBean1")).isNotNull();
        assertThat(beanFactory.getBeanDefinition("carBean2")).isNotNull();

        assertThat(beanFactory.getType("carBean")).isEqualTo(CarBean.class);
        assertThat(beanFactory.getType("&carBean")).isEqualTo(CarFactoryBean.class);
    }

    /**
     * 非延迟单例工厂Bean,创建单例对象
     */
    @Test
    public void testFactoryBean1() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/specialbean/factoryBean.xml");

        // 3 预加载单例Bean,已加载单例Bean会放到单例对象池中(无法加载延迟单例Bean)
        beanFactory.preInstantiateSingletons();

        // 4 从单例缓存池中获取预加载单例Bean,对于FactoryBean,是将单例FactoryBean对象被放到单例对象池中
        Object beanFactorySingleton = beanFactory.getSingleton("carBean");
        assertThat(beanFactorySingleton instanceof CarFactoryBean).isTrue();

        // 5.1 判断获取Bean对象是否是单例的，如果是单例从单例对象池中获取，这里获取的FactoryBean，
        // 5.2 如果单例对象池中不存在，新创建添加到单例对象池
        // 5.3 Bean类型是FactoryBean，通过getObjectForBeanInstance获取Bean
        // getObjectForBeanInstance 方法中
        // 5.4 如果参数名称为 "carBean"，表示获取工厂创建对象，调用FactoryBean.getObject()，返回
        //     如果参数名称为 "&carBean",表示获取工厂，返回单例对象池中对象FactoryBean，
        CarBean carBean1 = beanFactory.getBean("carBean", CarBean.class);
        CarBean carBean2 = beanFactory.getBean("carBean", CarBean.class);
        CarFactoryBean carFactoryBean1 = beanFactory.getBean("&carBean", CarFactoryBean.class);
        CarFactoryBean carFactoryBean2 = beanFactory.getBean("&carBean", CarFactoryBean.class);
        assertThat(beanFactory.getType("carBean")).isEqualTo(CarBean.class);
        assertThat(beanFactory.getType("&carBean")).isEqualTo(CarFactoryBean.class);

        // 6 由于carBean 是单例Bean多次获取都是同一个Bean
        assertThat(carFactoryBean1).isEqualTo(carFactoryBean2);

        // 7 "carBean" FactoryBean.getObject()获取Bean对下每次都是同一个对象
        assertThat(carBean1).isEqualTo(carBean2);
    }


    /**
     * 非延迟单例工厂Bean，创建原型对象
     */
    @Test
    public void testFactoryBean2() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/specialbean/factoryBean.xml");

        // 3 预加载单例Bean,已加载单例Bean会放到单例对象池中(无法加载延迟单例Bean)
        beanFactory.preInstantiateSingletons();

        // 4 从单例缓存池中获取预加载单例Bean,对于FactoryBean,是将单例FactoryBean对象被放到单例对象池中
        // 由于carBean1 是延迟加载这里获取为null
        Object beanFactorySingleton = beanFactory.getSingleton("carBean2");
        assertThat(beanFactorySingleton instanceof CarFactoryBean).isTrue();

        // 5.1 判断获取Bean对象是否是单例的，如果是单例从单例对象池中获取，这里获取的FactoryBean，
        // 5.2 如果单例对象池中不存在，新创建添加到单例对象池
        // 5.3 Bean类型是FactoryBean，通过getObjectForBeanInstance获取Bean
        // 5.4 如果获取"carBean"，表示获取工厂创建对象，调用FactoryBean.getObject()，返回
        //     如果获取"&carBean",表示获取工厂，返回单例对象池中对象FactoryBean，
        CarBean carBean1 = beanFactory.getBean("carBean2", CarBean.class);
        CarBean carBean2 = beanFactory.getBean("carBean2", CarBean.class);
        CarFactoryBean carFactoryBean1 = beanFactory.getBean("&carBean2", CarFactoryBean.class);
        CarFactoryBean carFactoryBean2 = beanFactory.getBean("&carBean2", CarFactoryBean.class);

        // 6 由于carBean 是单例Bean多次获取都是同一个Bean
        assertThat(carFactoryBean1).isEqualTo(carFactoryBean2);

        // 7 "carBean" FactoryBean.getObject()获取Bean对下每次都不是同一个对象
        assertThat(carBean1==carBean2).isFalse();
    }


    /**
     * 延迟单例工厂Bean
     */
    @Test
    public void testFactoryBean3() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/specialbean/factoryBean.xml");

        // 3 预加载单例Bean,已加载单例Bean会放到单例对象池中(无法加载延迟单例Bean)
        beanFactory.preInstantiateSingletons();

        // 4 从单例缓存池中获取预加载单例Bean,对于FactoryBean,是将单例FactoryBean对象被放到单例对象池中
        // 由于carBean1 是延迟加载这里获取为null
        Object beanFactorySingleton = beanFactory.getSingleton("carBean1");
        assertThat(beanFactorySingleton).isNull();
    }

    /**
     * SmartFactoryBean 是一个特殊FactoryBean，
     * 相对于FactoryBean，如果重写isEagerInit()方法返回true，
     * 会在预加载时候调用getObject()方法
     */
    @Test
    public void testSmartFactoryBean1() {
        CarFactoryBean.reset();
        SmartCartFactoryBean.reset();

        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/specialbean/factoryBean.xml");

        // 3 预加载单例Bean,已加载单例Bean会放到单例对象池中(无法加载延迟单例Bean)
        beanFactory.preInstantiateSingletons();


        // 4 从单例缓存池中获取预加载单例Bean,对于FactoryBean,是将单例FactoryBean对象被放到单例对象池中
        Object beanFactorySingleton1 = beanFactory.getSingleton("carBean");
        assertThat(beanFactorySingleton1 instanceof CarFactoryBean).isTrue();
        assertThat(CarFactoryBean.wasinvokegetObject()).isFalse();

        Object beanFactorySingleton2 = beanFactory.getSingleton("carBean3");
        assertThat(beanFactorySingleton2 instanceof SmartCartFactoryBean).isTrue();
        assertThat(SmartCartFactoryBean.wasinvokegetObject()).isTrue();
    }

}
