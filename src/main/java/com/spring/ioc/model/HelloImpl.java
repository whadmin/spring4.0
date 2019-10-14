package com.spring.ioc.model;

public class HelloImpl implements HelloApi{

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public HelloImpl(){
        this.message = "Hello World!";
    }

    public HelloImpl(String message){
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
