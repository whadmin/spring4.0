package com.spring.ioc.bean.getBean.propertyInjection.beanObject.no_annotation;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/2 23:45
 */
public class IntegerTestBean {

    private int index;

    public void setIndex(int index) {
        this.index = index;
    }

    public void sayHello() {
        System.err.println(index);
    }
}
