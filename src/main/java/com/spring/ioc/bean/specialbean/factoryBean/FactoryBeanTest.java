package com.spring.ioc.bean.specialbean.factoryBean;

import com.spring.ioc.bean.specialbean.factoryBean.beanObject.Car;
import com.spring.ioc.bean.specialbean.factoryBean.beanObject.CarFactoryBean;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FactoryBeanTest {

    @Test
    public void testFactoryBean() {
        // 使用构造器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/bean/specialbean/factoryBean.xml");
        Car car = (Car) beanFactory.getBean("car");
        Assert.assertNotNull(car);
    }

    @Test
    public void testFactoryBeanObject() {
        // 使用构造器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/bean/specialbean/factoryBean.xml");
        CarFactoryBean carFactoryBean = (CarFactoryBean) beanFactory.getBean("&car");
        Assert.assertNotNull(carFactoryBean);
    }


    @Test
    public void testisFactoryBean() {
        // 使用构造器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/bean/specialbean/factoryBean.xml");
        Assert.assertTrue(((ClassPathXmlApplicationContext) beanFactory).getBeanFactory().isFactoryBean("car"));
    }
}
