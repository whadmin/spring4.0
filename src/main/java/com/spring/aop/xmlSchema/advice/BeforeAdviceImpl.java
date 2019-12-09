package com.spring.aop.xmlSchema.advice;

import org.springframework.aop.MethodBeforeAdvice;
import java.lang.reflect.Method;

/**
 * 拦截方法调用前执行
 *
 * before:13, BeforeAdviceImpl (com.spring.aop.xmlSchema.advice)
 * invoke:55, MethodBeforeAdviceInterceptor (org.springframework.aop.framework.adapter)
 * proceed:185, ReflectiveMethodInvocation (org.springframework.aop.framework)
 * invoke:92, ExposeInvocationInterceptor (org.springframework.aop.interceptor)
 * proceed:185, ReflectiveMethodInvocation (org.springframework.aop.framework)
 * invoke:212, JdkDynamicAopProxy (org.springframework.aop.framework)   //JDK生成代理对象
 * execute:-1, $Proxy5 (com.sun.proxy)           //代码对象
 */
public class BeforeAdviceImpl implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("===========before advice star");
        System.out.println("===========method:"+method);
        System.out.println("===========args:"+args);
        System.out.println("===========target:"+target);
        System.out.println("===========before advice end");
    }
}
