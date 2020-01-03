package com.spring.ioc.bean.assemblyBean.beanObject.no_annotation;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/1 22:16
 */
public class BeanObject2 {

    private String message;

    public BeanObject2() {
        this.message = "Hello World!";
    }

    public BeanObject2(String message) {
        this.message = message;
    }

    public void sayHello() {
        System.out.println(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void destroy() {
        System.out.println("destroy");
    }

    public void init() {
        System.out.println("init");
    }
}
