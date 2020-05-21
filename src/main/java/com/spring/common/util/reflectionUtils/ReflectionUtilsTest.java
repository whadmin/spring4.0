package com.spring.common.util.reflectionUtils;

import com.spring.common.proxy.Service.impl.HelloServiceImpl;
import com.spring.common.util.model.ITestService;
import com.spring.common.util.model.TestServiceImpl;
import org.junit.Test;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * @Author: wuhao.w
 * @Date: 2020/5/20 20:09
 */
public class ReflectionUtilsTest {

    /**
     * JDK getDeclaredMethods
     * 该方法是获取本类中的所有方法，包括私有的(private、protected、默认以及public)的方法。
     * <p>
     * ReflectionUtils.getDeclaredMethods
     * 对JDK方法进行了扩展
     * <p>
     * 1 内部调用JDK getDeclaredMethods 获取本类中的所有方法，包括私有的(private、protected、默认以及public)的方法。
     * 2 同时针对JDK8之后新特性default，同时还获取本类直接实现所有接口，以及其接口父接口中default方法
     */
    @Test
    public void getDeclaredMethods() throws IOException {
        System.out.println("=============JDK getDeclaredMethods=============");
        Method[] declaredMethods = TestServiceImpl.class.getDeclaredMethods();
        Arrays.asList(declaredMethods).forEach(method -> System.out.println(method));
        System.out.println("=============ReflectionUtils getDeclaredMethods=============");
        Method[] allDeclaredMethods = ReflectionUtils.getDeclaredMethods(TestServiceImpl.class);
        Arrays.asList(allDeclaredMethods).forEach(method -> System.out.println(method));
    }


    @Test
    public void findMethod() throws Exception {
        Method hehe = ITestService.class.getMethod("hehe");
        System.out.println(hehe);
        Method hehe1 = TestServiceImpl.class.getMethod("hehe");
        System.out.println(hehe1);
        Arrays.asList(TestServiceImpl.class.getMethods()).forEach(method -> System.out.println(method));
        ;


        System.out.println("=============ReflectionUtils getDeclaredMethods=============");
        Method test = ReflectionUtils.findMethod(TestServiceImpl.class, "test");
        System.out.println(test);
        Method itest = ReflectionUtils.findMethod(TestServiceImpl.class, "itest");
        System.out.println(itest);
        Method sayHello = ReflectionUtils.findMethod(TestServiceImpl.class, "sayHello", String.class);
        System.out.println(sayHello);

        Method test1 = TestServiceImpl.class.getMethod("test");
        System.out.println(test1);
        Method itest1 = TestServiceImpl.class.getMethod("itest");
        System.out.println(itest1);
        Method sayHello1 = TestServiceImpl.class.getMethod("sayHello", String.class);
        System.out.println(sayHello1);
    }

    @Test
    public void doWithMethods() throws Exception {
        ReflectionUtils.doWithMethods(TestServiceImpl.class, method -> {
            System.out.println(method);
        }, ReflectionUtils.USER_DECLARED_METHODS);
    }
}
