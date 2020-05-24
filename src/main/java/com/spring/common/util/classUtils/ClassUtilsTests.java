package com.spring.common.util.classUtils;

import com.spring.common.proxy.Service.impl.Base;
import com.spring.common.proxy.Service.impl.HelloService;
import com.spring.common.proxy.Service.impl.HelloServiceImpl;
import com.spring.common.proxy.Service.impl.IHelloService;
import com.spring.common.proxy.Service.proxy.HelloServiceCgLib;
import com.spring.common.proxy.Service.proxy.HelloServiceProxy;
import org.junit.Test;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/21 10:34
 */
public class ClassUtilsTests {

    private final ClassLoader classLoader = getClass().getClassLoader();

    public static void main(String[] args) throws NoSuchMethodException {
//        testGetUserClass();
        getAllInterfacesForClass();
    }


    public static void testGetUserClass(){
        HelloServiceCgLib helloServiceCgLib = new HelloServiceCgLib();
        HelloService proxy = (HelloService) helloServiceCgLib.getTarget(new HelloServiceImpl());
        System.out.println(ClassUtils.getUserClass(proxy.getClass()));
        System.out.println(proxy.getClass());

        HelloServiceProxy HelloHandler1 = new HelloServiceProxy();
        HelloService proxy1 = (HelloService) HelloHandler1.bind(new HelloServiceImpl());
        System.out.println(ClassUtils.getUserClass(proxy1.getClass()));
        System.out.println(proxy1.getClass());
    }

    /**
     *
     */
    public static void getAllInterfacesForClass(){
        Class<?>[] allInterfacesForClass = ClassUtils.getAllInterfacesForClass(HelloServiceImpl.class);
        Arrays.asList(allInterfacesForClass).forEach(p->System.out.println(p));
    }


    @Test
    public void getMostSpecificMethod(){
        Method method = ReflectionUtils.findMethod(IHelloService.class, "sayHello1",String.class);
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, HelloServiceImpl.class);
        Method specificMethod2 = ClassUtils.getMostSpecificMethod(method, HelloServiceImpl.class);
        System.out.println(method);
        System.out.println(specificMethod);
        System.out.println(specificMethod2);
        System.out.println(specificMethod==specificMethod2);
        System.out.println(specificMethod.equals(specificMethod2));
    }

    @Test
    public void getMostSpecificMethod2(){
        Method method = ReflectionUtils.findMethod(Base.class, "init");
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, HelloServiceImpl.class);
        Method specificMethod2 = ClassUtils.getMostSpecificMethod(method, HelloServiceImpl.class);
        System.out.println(method);
        System.out.println(specificMethod);
        System.out.println(specificMethod2);
        System.out.println(specificMethod==specificMethod2);
        System.out.println(specificMethod.equals(specificMethod2));
    }
}
