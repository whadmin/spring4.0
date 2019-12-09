package com.spring.aop.xmlSchema.advice;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/6 16:03
 *
 * 拦截方法调用后执行
 *
 * afterReturning:15, AfterReturningAdviceImpl (com.spring.aop.xmlSchema.advice)
 * invoke:56, AfterReturningAdviceInterceptor (org.springframework.aop.framework.adapter)
 * proceed:185, ReflectiveMethodInvocation (org.springframework.aop.framework)
 * invoke:55, AfterReturningAdviceInterceptor (org.springframework.aop.framework.adapter)
 * proceed:185, ReflectiveMethodInvocation (org.springframework.aop.framework)
 * invoke:56, MethodBeforeAdviceInterceptor (org.springframework.aop.framework.adapter)
 * proceed:185, ReflectiveMethodInvocation (org.springframework.aop.framework)
 * invoke:92, ExposeInvocationInterceptor (org.springframework.aop.interceptor)
 * proceed:185, ReflectiveMethodInvocation (org.springframework.aop.framework)
 * invoke:212, JdkDynamicAopProxy (org.springframework.aop.framework)
 * execute:-1, $Proxy5 (com.sun.proxy)
 */
public class AfterReturningAdviceImpl implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("===========after Returning star");
        System.out.println("===========o:"+o);
        System.out.println("===========method:"+method);
        System.out.println("===========objects:"+objects);
        System.out.println("===========o1:"+o1);
        System.out.println("===========after Returning end");
    }
}
