package com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation;

/**
 * @Author: wuhao.w
 * @Date: 2020/7/5 12:42
 */
public class PopulateBean {

    private String message;

    public PopulateBean() {
        this.message = "Hello World!";
    }

    public PopulateBean(String message) {
        this.message = message;
    }

    public void destroy() {
        System.out.println("destroy");
    }

    public void init() {
        System.out.println("init");
    }
}
