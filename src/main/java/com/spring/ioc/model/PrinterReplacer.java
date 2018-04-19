package com.spring.ioc.model;

import java.lang.reflect.Method;

import org.springframework.beans.factory.support.MethodReplacer;

public class PrinterReplacer implements MethodReplacer {

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.println("Print Replacer");
        //注意此处不能再通过反射调用了,否则会产生循环调用，知道内存溢出
        //method.invoke(obj, new String[]{"hehe"});
        return null;
    }
}
