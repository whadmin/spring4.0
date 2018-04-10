package com.spring.ioc.model;

public class HelloImpl2 implements HelloApi2{

    private String message;

    public HelloImpl2(){
        this.message = "Hello World!";
    }

    public HelloImpl2(String message){
        this.message = message;
    }

    @Override
    public void sayHello() {
        System.out.println(message);
    }

    public void destroy() {
        System.out.println("destroy");
    }
    
    public void init() {
        System.out.println("init");
    }
}
