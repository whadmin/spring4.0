package com.spring.ioc.beanFactory.beanFactoryApi;

import com.spring.ioc.bean.specialbean.factoryBean.beanObject.CarBean;
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

import static org.assertj.core.api.Assertions.assertThat;

public class BeanFactoryAPITest {

    @Test
    public void testgetBeanForName() {

        // 1.初始化容器
        DefaultListableBeanFactory lbf = new DefaultListableBeanFactory();

        // 2 装配Bean
        Resource resource = new ClassPathResource("ioc/beanFactory/beanFactoryApi.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(lbf);
        reader.loadBeanDefinitions(resource);

        BeanFactory beanFactory=lbf;

        SimpleBean simpleBean = (SimpleBean) beanFactory.getBean("simpleBean");
        SimpleBean simpleBeanName = (SimpleBean) beanFactory.getBean("simpleBeanName");
        SimpleBean simpleBean1 = (SimpleBean) beanFactory.getBean("alias1");
        SimpleBean simpleBean2 = (SimpleBean) beanFactory.getBean("alias2");
        SimpleBean simpleBean3 = (SimpleBean) beanFactory.getBean("alias3");
        SimpleBean simpleBean4 = (SimpleBean) beanFactory.getBean("alias4");
        Assert.assertNotNull(simpleBean);
        Assert.assertNotNull(simpleBeanName);
        Assert.assertNotNull(simpleBean1);
        Assert.assertNotNull(simpleBean2);
        Assert.assertNotNull(simpleBean3);
        Assert.assertNotNull(simpleBean4);
    }

    @Test
    public void testgetBeanForType() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/beanFactory/beanFactoryApi.xml");
        ConfigurableListableBeanFactory beanFactory = ((ClassPathXmlApplicationContext) context).getBeanFactory();
        SimpleBean simpleBean = (SimpleBean) beanFactory.getBean(SimpleBean.class);
        Assert.assertNotNull(simpleBean);
    }

    @Test
    public void testgetBeanForNameAndType() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/beanFactory/beanFactoryApi.xml");
        ConfigurableListableBeanFactory beanFactory = ((ClassPathXmlApplicationContext) context).getBeanFactory();
        SimpleBean simpleBean = (SimpleBean) beanFactory.getBean("simpleBean", SimpleBean.class);
        Assert.assertNotNull(simpleBean);
    }

    @Test
    public void testgetBeanForNameAndArg() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/beanFactory/beanFactoryApi.xml");
        ConfigurableListableBeanFactory beanFactory = ((ClassPathXmlApplicationContext) context).getBeanFactory();
        SimpleBean simpleBean = (SimpleBean) beanFactory.getBean("simpleBean", "test");
        Assert.assertNotNull(simpleBean);
        Assert.assertEquals(simpleBean.getMessage(), "test");
    }

    @Test
    public void testgetBeanForTypeAndArg() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/beanFactory/beanFactoryApi.xml");
        ConfigurableListableBeanFactory beanFactory = ((ClassPathXmlApplicationContext) context).getBeanFactory();
        SimpleBean simpleBean = (SimpleBean) beanFactory.getBean(SimpleBean.class, "test");
        Assert.assertNotNull(simpleBean);
        Assert.assertEquals(simpleBean.getMessage(), "test");
    }

    @Test
    public void testContainsBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/beanFactory/beanFactoryApi.xml");
        ConfigurableListableBeanFactory beanFactory = ((ClassPathXmlApplicationContext) context).getBeanFactory();
        Assert.assertTrue(beanFactory.containsBean("test"));
    }

    @Test
    public void testIsSingleton() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/beanFactory/beanFactoryApi.xml");
        ConfigurableListableBeanFactory beanFactory = ((ClassPathXmlApplicationContext) context).getBeanFactory();
        Assert.assertTrue(beanFactory.isSingleton("test"));
    }

    @Test
    public void testIsPrototype() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/beanFactory/beanFactoryApi.xml");
        ConfigurableListableBeanFactory beanFactory = ((ClassPathXmlApplicationContext) context).getBeanFactory();
        Assert.assertFalse(beanFactory.isPrototype("test"));
    }

    @Test
    public void testisTypeMatchResolvableType() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/beanFactory/beanFactoryApi.xml");
        ConfigurableListableBeanFactory beanFactory = ((ClassPathXmlApplicationContext) context).getBeanFactory();
        Assert.assertTrue(beanFactory.isTypeMatch("test", ResolvableType.forClass(SimpleBean.class)));
    }

    @Test
    public void testisTypeMatchClass() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/beanFactory/beanFactoryApi.xml");
        ConfigurableListableBeanFactory beanFactory = ((ClassPathXmlApplicationContext) context).getBeanFactory();
        Assert.assertTrue(beanFactory.isTypeMatch("test", SimpleBean.class));
    }

    @Test
    public void testGetType() {
        // 1.初始化容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2 装配Bean
        Resource resource = new ClassPathResource("ioc/beanFactory/beanFactoryApi.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        assertThat(beanFactory.getType("carBean")).isEqualTo(CarBean.class);
        Assert.assertEquals(beanFactory.getType("test"), SimpleBean.class);
    }


    @Test
    public void test2() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/bean/ability/alias/aliasBean.xml");

    }

    @Test
    public void test3() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/bean/ability/alias/aliasBean.xml");
        // 根据name获取bean
        HelloApi bean = beanFactory.getBean("bean2", HelloApi.class);
        bean.sayHello();
    }

    @Test
    public void test4() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/bean/ability/alias/aliasBean.xml");
        // 根据id获取bean
        HelloApi bean3 = beanFactory.getBean("bean3", HelloApi.class);
        bean3.sayHello();
        // 根据别名获取bean
        HelloApi bean3_alias1 = beanFactory.getBean("bean3_alias1", HelloApi.class);
        bean3_alias1.sayHello();
        // 根据id获取bean
        HelloApi bean4 = beanFactory.getBean("bean4", HelloApi.class);
        bean4.sayHello();

        String[] bean4Alias = beanFactory.getAliases("bean4");
        // 因此别名不能和id一样，如果一样则由IoC容器负责消除冲突
        junit.framework.Assert.assertEquals(0, bean4Alias.length);

    }

    @Test
    public void test5() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/bean/ability/alias/aliasBean.xml");
        // 根据id获取bean
        HelloApi bean1 = beanFactory.getBean("bean5", HelloApi.class);
        bean1.sayHello();
        // 2根据别名获取bean
        HelloApi alias11 = beanFactory.getBean("bean5_alias11", HelloApi.class);
        alias11.sayHello();
        // 验证确实是四个别名
        String[] bean1Alias = beanFactory.getAliases("bean5");
        System.out.println("=======GetBeanContainer.xml bean5 别名========");
        for (String alias : bean1Alias) {
            System.out.println(alias);
        }
        System.out.println("=======GetBeanContainer.xml bean5 别名========");
        junit.framework.Assert.assertEquals(4, bean1Alias.length);

        // 根据id获取bean
        HelloApi bean6 = beanFactory.getBean("bean6", HelloApi.class);
        bean6.sayHello();
        // //2根据别名获取bean
        HelloApi bean6_alias21 = beanFactory.getBean("bean6_alias21", HelloApi.class);
        bean6_alias21.sayHello();
        // 验证确实是两个别名
        String[] bean6Alias = beanFactory.getAliases("bean6");
        System.out.println("=======namingbean5.xml bean2 别名========");
        for (String alias : bean6Alias) {
            System.out.println(alias);
        }
        System.out.println("=======namingbean5.xml bean2 别名========");
        junit.framework.Assert.assertEquals(2, bean6Alias.length);

    }

    @Test
    public void test6() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/bean/ability/alias/aliasBean.xml");
        // 根据id获取bean
        HelloApi bean = beanFactory.getBean("bean7", HelloApi.class);
        bean.sayHello();

        // 根据别名获取bean
        HelloApi alias1 = beanFactory.getBean("bean7_alias1", HelloApi.class);
        alias1.sayHello();
        HelloApi alias2 = beanFactory.getBean("bean7_alias2", HelloApi.class);
        alias2.sayHello();

        String[] beanAlias = beanFactory.getAliases("bean7");
        System.out.println("=======namingbean6.xml bean 别名========");
        for (String alias : beanAlias) {
            System.out.println(alias);
        }
        System.out.println("=======namingbean6.xml bean 别名========");
        junit.framework.Assert.assertEquals(2, beanAlias.length);

    }
}
