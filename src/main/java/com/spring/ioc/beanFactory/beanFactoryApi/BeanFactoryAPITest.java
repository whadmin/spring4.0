package com.spring.ioc.beanFactory.beanFactoryApi;

import com.spring.ioc.bean.specialbean.factoryBean.beanObject.CarBean;
import com.spring.ioc.beanFactory.beanObject.SimpleBean;
import org.junit.Assert;
import org.junit.Test;
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
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "ioc/beanFactory/beanFactoryApi.xml");
        ConfigurableListableBeanFactory beanFactory = ((ClassPathXmlApplicationContext) context).getBeanFactory();
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
}
