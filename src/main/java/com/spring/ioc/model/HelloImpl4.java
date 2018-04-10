package com.spring.ioc.model;

public class HelloImpl4 implements HelloApi {

    private String message;
    private int index;
    
    //需要空的构造器
    public HelloImpl4() {
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    @Override
    public void sayHello() {
        System.out.println(index + ":" + message);
    }
    
}
