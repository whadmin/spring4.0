package com.spring.ioc.bean.xml;

import com.spring.ioc.model.Car;
import com.spring.ioc.model.CarFactoryBean;
import com.spring.ioc.model.HelloApi;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InstantiationFactoryBean {

    @Test
    public void testInstantiatingBeanByConstructor() {
        // 使用构造器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/InstantiationFactoryBean.xml");
        Car car = (Car) beanFactory.getBean("car");
        System.out.println(car);
        CarFactoryBean carFactoryBean = (CarFactoryBean) beanFactory.getBean("&car");
        System.out.println(carFactoryBean);
    }
}
