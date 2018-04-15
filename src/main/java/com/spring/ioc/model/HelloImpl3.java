package com.spring.ioc.model;


public class HelloImpl3 implements HelloApi {

    private String message;
    private int index;
    
    public HelloImpl3() {
    }
    
    public HelloImpl3(String message, int index) {
        this.message = message;
        this.index = index;
    }
    
    @Override
    public void sayHello() {
        System.out.println(index + ":" + message);
    }
    
    public void destroy() {
        System.out.println("destroy");
    }
    
    public void init() {
        System.out.println("init");
    }
    
}
