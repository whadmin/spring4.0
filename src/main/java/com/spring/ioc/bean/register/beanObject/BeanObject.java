package com.spring.ioc.bean.register.beanObject;

public class BeanObject {

    private String message;

    public BeanObject() {
        this.message = "Hello World!";
    }

    public BeanObject(String message) {
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
