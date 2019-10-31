package com.spring.ioc.bean.specialbean;

import com.spring.ioc.bean.specialbean.beanObject.Car;
import com.spring.ioc.bean.specialbean.beanObject.CarFactoryBean;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FactoryBean {
    @Test
    public void testFactoryBean() {
        // 使用构造器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "ioc/bean/specialbean/factoryBean.xml");
        Car car = (Car) beanFactory.getBean("car");
        System.out.println(car);
        CarFactoryBean carFactoryBean = (CarFactoryBean) beanFactory.getBean("&car");
        System.out.println(carFactoryBean);
    }
}
