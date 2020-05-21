package com.spring.common.util.classUtils;

import com.spring.common.proxy.Service.impl.HelloService;
import com.spring.common.proxy.Service.impl.HelloServiceImpl;
import com.spring.common.proxy.Service.proxy.HelloServiceCgLib;
import com.spring.common.proxy.Service.proxy.HelloServiceProxy;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

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
     * inspect
     */
    public static void getAllInterfacesForClass(){
        Class<?>[] allInterfacesForClass = ClassUtils.getAllInterfacesForClass(HelloServiceImpl.class);
        Arrays.asList(allInterfacesForClass).forEach(p->System.out.println(p));
    }
}
