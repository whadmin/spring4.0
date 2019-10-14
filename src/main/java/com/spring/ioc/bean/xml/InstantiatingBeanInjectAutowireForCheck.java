package com.spring.ioc.bean.xml;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class InstantiatingBeanInjectAutowireForCheck {
    
    @Test(expected = UnsatisfiedDependencyException.class)
    public void testDependencyCheckByObject() throws IOException {
        //将抛出异常
        new ClassPathXmlApplicationContext("ioc/dependency-check-object.xml");
    }

    @Test(expected = UnsatisfiedDependencyException.class)
    public void testDependencyCheckBySimple() throws IOException {
        //将抛出异常
        new ClassPathXmlApplicationContext("ioc/dependency-check-simple.xml");
    }
   
    @Test(expected = UnsatisfiedDependencyException.class)
    public void testDependencyCheckByAll() throws IOException {
        //将抛出异常
        new ClassPathXmlApplicationContext("ioc/dependency-check-all.xml");
    }

    @Test
    public void testDependencyCheckByNone() throws IOException {
        //将抛出异常
        new ClassPathXmlApplicationContext("ioc/dependency-check-none.xml");
    }
    
}

