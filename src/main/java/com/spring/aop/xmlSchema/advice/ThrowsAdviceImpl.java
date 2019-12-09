package com.spring.aop.xmlSchema.advice;

import org.springframework.aop.ThrowsAdvice;

import javax.servlet.ServletException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/6 17:40
 *
 * afterThrowing:16, ThrowsAdviceImpl (com.spring.aop.xmlSchema.advice)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:498, Method (java.lang.reflect)   //通过反射调用
 * invokeHandlerMethod:154, ThrowsAdviceInterceptor (org.springframework.aop.framework.adapter)
 * invoke:117, ThrowsAdviceInterceptor (org.springframework.aop.framework.adapter)   //调用来源于ThrowsAdviceInterceptor
 * proceed:185, ReflectiveMethodInvocation (org.springframework.aop.framework)
 * invoke:56, MethodBeforeAdviceInterceptor (org.springframework.aop.framework.adapter)
 * proceed:185, ReflectiveMethodInvocation (org.springframework.aop.framework)
 * invoke:92, ExposeInvocationInterceptor (org.springframework.aop.interceptor)
 * proceed:185, ReflectiveMethodInvocation (org.springframework.aop.framework)
 * invoke:212, JdkDynamicAopProxy (org.springframework.aop.framework)
 * executeError:-1, $Proxy5 (com.sun.proxy)
 */
public class ThrowsAdviceImpl implements ThrowsAdvice {

    public void afterThrowing(Exception ex) {
        System.out.println("===========afterThrowing Exception:" + ex);
    }

    public void afterThrowing(RemoteException ex) {
        System.out.println("===========afterThrowing RemoteException:" + ex);
    }

    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
        System.out.println("===========afterThrowing Exception:" + ex);
    }

    public void afterThrowing(Method method, Object[] args, Object target, ServletException ex) {
        System.out.println("===========afterThrowing ServletException:" + ex);
    }
}
