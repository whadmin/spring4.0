package com.spring.ioc.bean.getBean.methodInjection;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.Printer;



public class BeanMethodInject {
    
    @Test
    public void testLookup() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ioc/lookupMethodInject.xml");
        System.out.println("=======singleton sayHello======");
        HelloApi helloApi1 = context.getBean("helloApi1", HelloApi.class);
        helloApi1.sayHello();
        helloApi1 = context.getBean("helloApi1", HelloApi.class);
        helloApi1.sayHello();
        System.out.println("=======prototype sayHello======");
        HelloApi helloApi2 = context.getBean("helloApi2", HelloApi.class);
        helloApi2.sayHello();
        helloApi2 = context.getBean("helloApi2", HelloApi.class);
        helloApi2.sayHello();
    }
    
    @Test
    public void testMethodReplacer() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ioc/methodReplacerInject.xml");
        Printer printer = context.getBean("printer", Printer.class);
        printer.print("我将被替换");
    }
}

