package com.spring.common.proxy.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ReflectService
 * description TODO
 * create by lxj 2018/4/27
 **/
public class ReflectService {
    /**
     * 服务方法
     *
     * @param name
     */
    public void sayHello(String name) {
        System.out.println("hello " + name);
    }

    /**
     * 测试入口
     *
     * @param args
     */
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException, InstantiationException {
        /*// 方法一
        // 通过反射创建ReflectService对象
        //Object service = Class.forName(ReflectService.class.getName());
        Class<?> reflectService = ReflectService.class;
        // 获取服务方法
        *//*Method[] methods = reflectService.getMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.println(methods[i].getName());
        }*//*
        Method method = reflectService.getDeclaredMethod("sayHello", String.class);
        // 反射调用方法
        method.invoke(reflectService.newInstance(), "张三");*/
        // 方法二
        Object service = Class.forName(ReflectService.class.getName()).newInstance();
        Method method = service.getClass().getMethod("sayHello", String.class);
        method.invoke(service,"zhangsan");
    }
}
