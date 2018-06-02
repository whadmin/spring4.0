package com.mybatis.chapter6.Service.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * HelloServiceProxy
 * description HelloService的代理类
 * create by lxj 2018/4/27
 **/
public class HelloServiceProxy implements InvocationHandler {
    // 真实服务的对象
    private Object target;

    /**
     * 绑定委托对象，并返回一个代理类
     *
     * @param target
     * @return
     */
    public Object bind(Object target) {
        this.target = target;
        // 取得代理对象，第一个参数是类加载器，第二个参数是接口（代理对象挂在哪个接口下），第三个参数是当前的HelloServiceProxy代理类
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("==========我是JDK动态代理=============");
        Object result = null;
        // 反射方法前调用
        System.out.println("我准备说hello。");
        // 执行方法，相当于调用HelloServiceImpl类的sayHello方法
        result = method.invoke(target, args);
        // 反射方法调用后
        System.out.println("我说过Hello了");
        return result;
    }
}