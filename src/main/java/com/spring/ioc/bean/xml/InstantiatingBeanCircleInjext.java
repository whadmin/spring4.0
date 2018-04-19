package com.spring.ioc.bean.xml;

import org.junit.Test;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 对于循环依赖   你要了解
 * 1  构造方法是无法解决
 * 2  set方法注入为Prototype无法解决
 * 3  set方法注入为单利设置ctx.setAllowCircularReferences(false);解决
 *  具体步骤如下：
       1、Spring容器创建单例“circleA” Bean，首先根据无参构造器创建Bean，并暴露一个“ObjectFactory ”用于返回一个提前暴露一个创建中的Bean，并将“circleA” 标识符放到“当前创建Bean池”；然后进行setter注入“circleB”；
       2、Spring容器创建单例“circleB” Bean，首先根据无参构造器创建Bean，并暴露一个“ObjectFactory”用于返回一个提前暴露一个创建中的Bean，并将“circleB” 标识符放到“当前创建Bean池”，然后进行setter注入“circleC”；
       3、Spring容器创建单例“circleC” Bean，首先根据无参构造器创建Bean，并暴露一个“ObjectFactory ”用于返回一个提前暴露一个创建中的Bean，并将“circleC” 标识符放到“当前创建Bean池”，然后进行setter注入“circleA”；
       进行注入“circleA”时由于提前暴露了“ObjectFactory”工厂从而使用它返回提前暴露一个创建中的Bean；
4、最后在依赖注入“circleB”和“circleA”，完成setter注入。
 * @author xu.jianguo
 *
 */
public class InstantiatingBeanCircleInjext {
    
    @Test(expected = BeanCurrentlyInCreationException.class)
    public void testcircleByConstructor() throws Throwable {
        try {
            new ClassPathXmlApplicationContext("ioc/circleInjectByConstructor.xml");
        }
        catch (Exception e) {
            //因为要在创建circle3时抛出；
            Throwable e1 = e.getCause().getCause().getCause();
            throw e1;
        }
    }
    @Test
    public void testCircleBySetterAndSingleton1() throws Throwable {
        new ClassPathXmlApplicationContext("ioc/circleInjectBySetterAndSingleton.xml");
    }

    @Test(expected = BeanCurrentlyInCreationException.class)
    public void testCircleBySetterAndSingleton2() throws Throwable {
        try {
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
            ctx.setConfigLocation("ioc/circleInjectBySetterAndSingleton.xml");
            ctx.setAllowCircularReferences(true);
            ctx.refresh();
        }
        catch (Exception e) {
            //因为要在创建circle3时抛出；
            Throwable e1 = e.getCause().getCause().getCause();
            throw e1;
        }
        System.out.println("!!!!!!!!!!!!!!!");
       
    }
    
    @Test(expected = BeanCurrentlyInCreationException.class)
    public void circleBySetterAndPrototypeTest() throws Throwable {
        try {
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("ioc/circleInjectBySetterAndPrototype.xml");
            
            System.out.println(ctx.getBean("circleA"));
        }
        catch (Exception e) {
            //因为要在创建circle3时抛出；
            Throwable e1 = e.getCause().getCause().getCause();
            throw e1;
        }
    }
    
}

