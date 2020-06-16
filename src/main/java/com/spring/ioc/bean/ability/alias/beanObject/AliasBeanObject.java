package com.spring.ioc.bean.ability.alias.beanObject;

import lombok.Data;

@Data
public class AliasBeanObject {

    private String message;

    public AliasBeanObject() {
        this.message = "Hello World!";
    }

    public AliasBeanObject(String message) {
        this.message = message;
    }

    public void destroy() {
        System.out.println("destroy");
    }

    public void init() {
        System.out.println("init");
    }
}
