package com.spring.ioc.beanFactory.beanObject;

public class SimpleBean {

    private String message;

    public SimpleBean() {
        this.message = "Hello World!";
    }

    public SimpleBean(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
