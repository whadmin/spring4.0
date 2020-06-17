package com.spring.ioc.beanFactory.beanFactoryApi;

import com.spring.BaseTest;
import com.spring.ioc.bean.ability.alias.beanObject.AliasBeanObject;
import com.spring.ioc.bean.specialbean.factoryBean.beanObject.CarBean;
import com.spring.ioc.bean.specialbean.factoryBean.beanObject.CarFactoryBean;
import com.spring.ioc.beanFactory.beanObject.SimpleBean;
import com.spring.ioc.model.HelloApi;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BeanFactoryAPITest extends BaseTest {

    /**
     * 通过名称name获取Bean
     */
    @Test
    public void testgetBeanForName() {
        // 1.初始化容器
        DefaultListableBeanFactory lbf = new DefaultListableBeanFactory();

        // 2 xml配置文件装配Bean
        xmlAssembly(lbf, "ioc/beanFactory/beanFactoryApi.xml");
        BeanFactory beanFactory = lbf;

        // 传入名称可以是 BeanId
        AliasBeanObject simpleBean = (AliasBeanObject) beanFactory.getBean("simpleBean");
        assertThat(simpleBean).isNotNull();

        // 传入名称可以是 Bean别名
        AliasBeanObject aliasBeanObject = (AliasBeanObject) beanFactory.getBean("bean1_alias");
        assertThat(aliasBeanObject).isNotNull();

        // Bean类型是FactoryBean  "carBean" 表示获取FactoryBean创建的对象，内部调用调用FactoryBean.getObject()
        CarBean carBean1 = (CarBean) beanFactory.getBean("carBean");
        assertThat(carBean1).isNotNull();

        // Bean类型是FactoryBean  "carBean" 表示获取FactoryBean对象，
        CarFactoryBean carFactoryBean1 = (CarFactoryBean) beanFactory.getBean("&carBean");
        assertThat(carFactoryBean1).isNotNull();
    }

    /**
     * 通过类型获取Bean
     * 这里需要保证IOC容器指定类型Bean是唯一的
     */
    @Test
    public void testgetBeanForType() {
        // 1.初始化容器
        DefaultListableBeanFactory lbf = new DefaultListableBeanFactory();

        // 2 xml配置文件装配Bean
        xmlAssembly(lbf, "ioc/beanFactory/beanFactoryApi.xml");
        BeanFactory beanFactory = lbf;

        SimpleBean simpleBean = beanFactory.getBean(SimpleBean.class);
        // 通过类型获取Bean
        assertThat(simpleBean).isNotNull();

        // 通过类型获取Bean，指定类型存在多个Bean 抛出异常
        assertThatThrownBy(() -> {beanFactory.getBean(AliasBeanObject.class);});
    }

    /**
     * 通过名称和类型获取Bean
     */
    @Test
    public void testgetBeanForNameAndType() {
        // 1.初始化容器
        DefaultListableBeanFactory lbf = new DefaultListableBeanFactory();

        // 2 xml配置文件装配Bean
        xmlAssembly(lbf, "ioc/beanFactory/beanFactoryApi.xml");
        BeanFactory beanFactory = lbf;

        // 传入名称可以是 BeanId
        AliasBeanObject simpleBean = beanFactory.getBean("simpleBean", AliasBeanObject.class);
        assertThat(simpleBean).isNotNull();

        // 传入名称可以是 Bean别名
        AliasBeanObject aliasBeanObject = beanFactory.getBean("bean1_alias", AliasBeanObject.class);
        assertThat(aliasBeanObject).isNotNull();

        // Bean类型是FactoryBean  "carBean" 表示获取FactoryBean创建的对象，内部调用调用FactoryBean.getObject()
        CarBean carBean1 = beanFactory.getBean("carBean", CarBean.class);
        assertThat(carBean1).isNotNull();

        // Bean类型是FactoryBean  "carBean" 表示获取FactoryBean对象，
        CarFactoryBean carFactoryBean1 = beanFactory.getBean("&carBean", CarFactoryBean.class);
        assertThat(carFactoryBean1).isNotNull();
    }

    /**
     * 通过名称和构造参数获取Bean
     */
    @Test
    public void testgetBeanForNameAndArg() {
        // 1.初始化容器
        DefaultListableBeanFactory lbf = new DefaultListableBeanFactory();

        // 2 xml配置文件装配Bean
        xmlAssembly(lbf, "ioc/beanFactory/beanFactoryApi.xml");
        BeanFactory beanFactory = lbf;

        // 传入名称可以是 BeanId
        AliasBeanObject simpleBean = (AliasBeanObject)beanFactory.getBean("simpleBean","test");
        assertThat(simpleBean).isNotNull();
        assertThat(simpleBean.getMessage()).isEqualTo("test");

        // 传入名称可以是 Bean别名
        AliasBeanObject aliasBeanObject = (AliasBeanObject)beanFactory.getBean("bean1_alias", "test");
        assertThat(aliasBeanObject.getMessage()).isEqualTo("test");
    }

    /**
     * 通过类型和构造参数获取Bean
     */
    @Test
    public void testgetBeanForTypeAndArg() {
        // 1.初始化容器
        DefaultListableBeanFactory lbf = new DefaultListableBeanFactory();

        // 2 xml配置文件装配Bean
        xmlAssembly(lbf, "ioc/beanFactory/beanFactoryApi.xml");
        BeanFactory beanFactory = lbf;

        // 通过类型和参数获取Bean
        SimpleBean simpleBean = beanFactory.getBean(SimpleBean.class, "test");
        assertThat(simpleBean).isNotNull();
        assertThat(simpleBean.getMessage()).isEqualTo("test");
    }

    /**
     * 判断是否包含此bean
     */
    @Test
    public void testContainsBean() {
        // 1.初始化容器
        DefaultListableBeanFactory lbf = new DefaultListableBeanFactory();

        // 2 xml配置文件装配Bean
        xmlAssembly(lbf, "ioc/beanFactory/beanFactoryApi.xml");
        BeanFactory beanFactory = lbf;

        // 传入名称可以是 BeanId
        assertThat(beanFactory.containsBean("simpleBean")).isTrue();
        assertThat(beanFactory.containsBean("bean1_alias")).isTrue();

        // Bean类型是FactoryBean  BeanDefinition描述的是FactoryBean，对于名称为BeanId "carBean"
        assertThat(lbf.containsBeanDefinition("carBean")).isTrue();
        assertThat(lbf.containsBeanDefinition("&carBean")).isFalse();

        // Bean类型是FactoryBean containsBean 会将 "&carBean" 内部会自动转换成 "carBean"
        assertThat(beanFactory.containsBean("carBean")).isTrue();
        assertThat(beanFactory.containsBean("&carBean")).isTrue();
    }

    /**
     * 判断是否是单例类型
     */
    @Test
    public void testIsSingleton() {
        // 1.初始化容器
        DefaultListableBeanFactory lbf = new DefaultListableBeanFactory();

        // 2 xml配置文件装配Bean
        xmlAssembly(lbf, "ioc/beanFactory/beanFactoryApi.xml");
        BeanFactory beanFactory = lbf;

        assertThat(beanFactory.isSingleton("singletonBean")).isTrue();
        assertThat(beanFactory.isSingleton("singletonBean2")).isTrue();
    }

    /**
     * 判断是否是原型类型
     */
    @Test
    public void testIsPrototype() {
        // 1.初始化容器
        DefaultListableBeanFactory lbf = new DefaultListableBeanFactory();

        // 2 xml配置文件装配Bean
        xmlAssembly(lbf, "ioc/beanFactory/beanFactoryApi.xml");
        BeanFactory beanFactory = lbf;

        // 4 判断是否是原型类型
        assertThat(beanFactory.isPrototype("prototypeBean")).isTrue();
    }

    /**
     * 判断类型是否匹配（使用ResolvableType作为匹配条件）
     */
    @Test
    public void testisTypeMatchResolvableType() {
        // 1.初始化容器
        DefaultListableBeanFactory lbf = new DefaultListableBeanFactory();

        // 2 xml配置文件装配Bean
        xmlAssembly(lbf, "ioc/beanFactory/beanFactoryApi.xml");
        BeanFactory beanFactory = lbf;
        assertThat(beanFactory.isTypeMatch("simpleBean", ResolvableType.forClass(AliasBeanObject.class))).isTrue();
    }

    /**
     * 判断类型是否匹配（使用ResolvableType作为匹配条件）
     */
    @Test
    public void testisTypeMatchClass() {
        // 1.初始化容器
        DefaultListableBeanFactory lbf = new DefaultListableBeanFactory();

        // 2 xml配置文件装配Bean
        xmlAssembly(lbf, "ioc/beanFactory/beanFactoryApi.xml");
        BeanFactory beanFactory = lbf;
        assertThat(beanFactory.isTypeMatch("simpleBean", AliasBeanObject.class)).isTrue();
    }

    @Test
    public void testgetType() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        Resource resource = new ClassPathResource("ioc/beanFactory/beanFactoryApi.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        assertThat(beanFactory.getType("carBean")).isEqualTo(CarBean.class);
        assertThat(beanFactory.getType("&carBean")).isEqualTo(CarFactoryBean.class);
    }


    @Test
    public void testgetAliases(){
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        xmlAssembly(beanFactory, "ioc/bean/ability/alias/aliasBean.xml");

        assertThat(beanFactory.getAliases("alias_bean1")).isEqualTo(asArray("bean1_alias"));
        assertThat(beanFactory.getAliases("alias_bean2")).isEmpty();
        assertThat(beanFactory.getAliases("alias_bean3")).isEqualTo(asArray("bean3_alias4","bean3_alias1","bean3_alias2","bean3_alias3"));
        assertThat(beanFactory.getAliases("alias_bean4")).isEqualTo(asArray("bean4_alias2","bean4_alias1"));
        assertThat(beanFactory.getAliases("alias_bean5")).isEqualTo(asArray("bean5_alias2","bean5_alias1"));
    }

    static <T> T[] asArray(T... arr) {
        return arr;
    }
}
