package com.spring.common.util.reflectionUtils;

import com.spring.common.util.model.*;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/21 13:54
 */
public class ReflectionJDKTest {

    @Test
    public void getDeclaredMethods() throws NoSuchMethodException {
        List<Method> methods = Arrays.asList(TestService.class.getDeclaredMethods());
        methods.forEach(method -> {
            System.out.println(method);
        });
        System.out.println("==========================");
        List<Method> methods1 = Arrays.asList(ITestService.class.getDeclaredMethods());
        methods1.forEach(method -> {
            System.out.println(method);
        });
        System.out.println("==========================");
        List<Method> methods2 = Arrays.asList(AbstractTestService.class.getDeclaredMethods());
        methods2.forEach(method -> {
            System.out.println(method);
        });
        System.out.println("==========================");
        List<Method> methods3 = Arrays.asList(AbstractITestService.class.getDeclaredMethods());
        methods3.forEach(method -> {
            System.out.println(method);
        });
        System.out.println("==========================");
        List<Method> methods4 = Arrays.asList(TestServiceImpl2.class.getDeclaredMethods());
        methods4.forEach(method -> {
            System.out.println(method);
        });
        System.out.println("==========================");
        List<Method> methods5 = Arrays.asList(TestServiceImpl.class.getDeclaredMethods());
        methods5.forEach(method -> {
            System.out.println(method);
        });
    }


    @Test
    public void getMethods1() throws NoSuchMethodException {
        List<Method> methods = Arrays.asList(TestService.class.getMethods());
        methods.forEach(method -> {
            System.out.println(method);
        });
        System.out.println("==========================");
        List<Method> methods1 = Arrays.asList(ITestService.class.getMethods());
        methods1.forEach(method -> {
            System.out.println(methods);
            if (method.getName().equals("sayHello")) {
                try {
                    Method sayHello = TestService.class.getMethod("sayHello", String.class);
                    System.out.println(method == sayHello);
                } catch (NoSuchMethodException e) {
                }
            }
            if (method.getName().equals("test")) {
                try {
                    Method test = TestService.class.getMethod("test");
                    System.out.println(method == test);
                } catch (NoSuchMethodException e) {
                }
            }
        });

    }

    /**
     * 该方法是获取本类以及父类或者父接口中所有的公共方法(public修饰符修饰的)
     * <p>
     * 如果Class类型为抽象类，对于本类中抽象方法必须定于为(public修饰符修饰的)才会获取【抽象方法无法被定义成private】
     * 对于本类中抽象方法必须定于为(public修饰符修饰的)才会获取
     */
    @Test
    public void getMethods2() throws NoSuchMethodException {
        List<Method> methods = Arrays.asList(AbstractTestService.class.getMethods());
        methods.forEach(method -> {
            System.out.println(method);
            if (method.getName().equals("abstracttest")) {
                System.out.println("找到abstracttest方法");
            }
            if (method.getName().equals("simpletest")) {
                System.out.println("找到simpletest方法");
            }
            if (method.getName().equals("sayHello")) {
                try {
                    Method sayHello = TestService.class.getMethod("sayHello", String.class);
                    System.out.println(method == sayHello);
                } catch (NoSuchMethodException e) {
                }
            }
            if (method.getName().equals("test")) {
                try {
                    Method test = TestService.class.getMethod("test");
                    System.out.println(method == test);
                } catch (NoSuchMethodException e) {
                }
            }
        });
        System.out.println("==========================");

        List<Method> methods1 = Arrays.asList(AbstractITestService.class.getMethods());
        methods1.forEach(method -> {
            System.out.println(method);
            if (method.getName().equals("abstracttest")) {
                System.out.println("找到abstracttest方法");
            }
            if (method.getName().equals("simpletest")) {
                System.out.println("找到simpletest方法");
            }
            if (method.getName().equals("abstractitest")) {
                System.out.println("找到abstractitest方法");
            }
            if (method.getName().equals("simpleitest")) {
                System.out.println("找到simpleitest方法");
            }
            if (method.getName().equals("sayHello")) {
                try {
                    Method sayHello = TestService.class.getMethod("sayHello", String.class);
                    System.out.println(method == sayHello);
                } catch (NoSuchMethodException e) {
                }
            }
            if (method.getName().equals("test")) {
                try {
                    Method test = TestService.class.getMethod("test");
                    System.out.println(method == test);
                } catch (NoSuchMethodException e) {
                }
            }
        });
        System.out.println("==========================");
    }


    @Test
    public void getMethods3() throws NoSuchMethodException {
        List<Method> methods = Arrays.asList(TestServiceImpl2.class.getMethods());
        methods.forEach(method -> {
            System.out.println(method);
        });
    }
}
