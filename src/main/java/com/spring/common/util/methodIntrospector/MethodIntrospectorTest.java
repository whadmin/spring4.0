package com.spring.common.util.methodIntrospector;

import com.spring.common.proxy.Service.impl.HelloService;
import com.spring.common.proxy.Service.impl.HelloServiceBase;
import com.spring.common.proxy.Service.impl.HelloServiceImpl;
import com.spring.common.proxy.Service.impl.IHelloService;
import com.spring.common.proxy.Service.proxy.HelloServiceCgLib;
import com.spring.common.proxy.Service.proxy.HelloServiceProxy;
import com.spring.common.util.model.TestServiceImpl2;
import org.junit.Test;
import org.springframework.core.MethodIntrospector;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/21 10:26
 */
public class MethodIntrospectorTest {


    public static void main(String[] args) throws Exception {
//        selectMethods();
//        selectMethods2();
        selectMethods3();
//        linkedHashMap();
    }


    /**
     * 通过给定Class<?> userType，获取关联的方法，并通过MethodIntrospector.MetadataLookup查找到感兴趣的方法并返回
     *
     * 1 如果 Class<?> userType 类型是 Proxy.isProxyClass(targetType) JDK动态代理类型
     *   关联的方法：为其接口以及父接口上所有方法
     */
    @Test
    public static void selectMethods() throws IOException {
        System.out.println("===================================================");
        HelloServiceProxy HelloHandler1 = new HelloServiceProxy();
        HelloService proxy1 = (HelloService) HelloHandler1.bind(new HelloServiceImpl());
        Class<?> userType1 = ClassUtils.getUserClass(proxy1);
        Map<Method, Object> methodObjectMap = MethodIntrospector.selectMethods(userType1, (MethodIntrospector.MetadataLookup<Object>) method -> {
            try {
                return new Object();
            } catch (Throwable ex) {
                throw new IllegalStateException("Invalid mapping on handler class [" +
                        userType1 + "]: " + method, ex);
            }
        });
        methodObjectMap.keySet().forEach(method -> {System.out.println(method);});

    }

    /**
     * 通过给定Class<?> userType，获取关联的方法，并通过MethodIntrospector.MetadataLookup查找到感兴趣的方法并返回
     *
     * 1 如果 Class<?> userType 类型是 Proxy.isProxyClass(targetType) JDK动态代理类型
     *   关联的方法：为其接口以及父接口上所有方法
     */
    @Test
    public static void selectMethods2() throws IOException {
        System.out.println("===================================================");
        HelloServiceCgLib helloServiceCgLib = new HelloServiceCgLib();
        HelloService proxy1 = (HelloService) helloServiceCgLib.getTarget(new HelloServiceImpl());
        Class<?> userType = ClassUtils.getUserClass(proxy1);
        Map<Method, Object> methodObjectMap = MethodIntrospector.selectMethods(userType, (MethodIntrospector.MetadataLookup<Object>) method -> {
            try {
                return new Object();
            } catch (Throwable ex) {
                throw new IllegalStateException("Invalid mapping on handler class [" +
                        userType + "]: " + method, ex);
            }
        });
        methodObjectMap.keySet().forEach(method -> {System.out.println(method);});
    }

    @Test
    public static void selectMethods3() throws IOException {
        System.out.println("===================================================");
        HelloService proxy1 = new HelloServiceImpl();
        Class<?> userType = ClassUtils.getUserClass(proxy1);
        Map<Method, Object> methodObjectMap = MethodIntrospector.selectMethods(userType, (MethodIntrospector.MetadataLookup<Object>) method -> {
            try {
                return new Object();
            } catch (Throwable ex) {
                throw new IllegalStateException("Invalid mapping on handler class [" +
                        userType + "]: " + method, ex);
            }
        });
        methodObjectMap.keySet().forEach(method -> {System.out.println(method);});
    }

    public static void linkedHashMap() throws IOException {
        final Map<Method, String> methodMap = new LinkedHashMap<>();
        List<Method> methods1 = Arrays.asList(ReflectionUtils.getDeclaredMethods(HelloServiceImpl.class));
        methods1.forEach(method -> methodMap.put(method,"1"));
        List<Method> methods2 = Arrays.asList(ReflectionUtils.getDeclaredMethods(HelloServiceBase.class));
        methods2.forEach(method -> methodMap.put(method,"1"));

        Method sayHello1 = ReflectionUtils.findMethod(IHelloService.class, "sayHello1",String.class);
        Method specificMethod = ClassUtils.getMostSpecificMethod(sayHello1, HelloServiceImpl.class);
        Method specificMethod2 = ClassUtils.getMostSpecificMethod(sayHello1, HelloServiceImpl.class);
        System.out.println(methodMap.put(specificMethod,"2"));

        Map<String, String> map = new HashMap<>();
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");
        map.put("key4","value4");
        map.put("key5","value5");

        map.put("key1","value5");
        System.out.println(map);
    }
}
