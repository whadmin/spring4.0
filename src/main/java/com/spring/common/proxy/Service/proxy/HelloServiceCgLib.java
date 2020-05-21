package com.spring.common.proxy.Service.proxy;



import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * HelloServiceCgLib
 * description CGLIB动态代理
 * create by lxj 2018/5/8
 **/
public class HelloServiceCgLib implements MethodInterceptor {
    private Object target;

    /**
     * 回调方法
     * @param obj
     * @param method
     * @param args
     * @param proxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("#############I am CGLIB ##############");
        // 反射方法前调用,一般用来做权限检查
        System.out.println("我准备说hello");
        Object returnObj = proxy.invokeSuper(obj,args);
        // 反射方法之后调用
        System.out.println("我说过hello了");
        return returnObj;
    }

    /**
     * 创建代理对象
     * @param target
     * @return
     */
    public Object getTarget(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }
}
