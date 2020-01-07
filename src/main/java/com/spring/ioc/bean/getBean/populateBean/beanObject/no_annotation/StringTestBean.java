package com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/2 23:41
 */
public class StringTestBean {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void sayHello() {
        System.err.println( message);
    }
}
