package com.spring.aop.xmlSchema.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/6 15:16
 */
public class TargetAspect {

    /**
     * 前置通知：在切入点匹配的方法之前执行，通过<aop:aspect>标签下的<aop:before>标签声明
     */
    public void beforeAdvice() {
        System.out.println("===========before advice");
    }

    /**
     * 前置通知带参数
     */
    public void beforeAdvice(String param) {
        System.out.println("===========before advice param:" + param);
    }

    /**
     * 在切入点匹配的方法返回时（完成时）执行，不管是正常返回还是抛出异常都执行，通过<aop:aspect>标签下的<aop:after >标签声明
     */
    public void afterFinallyAdvice() {
        System.out.println("===========after finally advice");
    }

    /**
     * 后置返回通知：在切入点匹配的方法正常返回时执行，通过<aop:aspect>标签下的<aop:after-returning>标签声明
     */
    public void afterReturningAdvice(Object retVal) {
        System.out.println("===========after returning advice retVal:" + retVal);
    }

    /**
     * 后置异常通知：在切入点匹配的方法抛出异常时执行，通过<aop:aspect>标签下的<aop:after-throwing>标签声明
     */
    public void afterThrowingAdvice(Exception exception) {
        System.out.println("===========after throwing advice exception:" + exception);
    }

    /**
     * 环绕着在切入点匹配的连接点处的方法所执行的通知，也就是目标方法执行前后所执行的通知，可通过<aop:aspect>标签下的<aop:around >标签声明
     */
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("===========around before advice");
        Object retVal = pjp.proceed(new Object[]{"replace"});
        System.out.println("===========around after advice");
        return retVal;
    }
}
