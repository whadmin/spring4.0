package com.spring.ioc.bean.specialbean.factoryBean.beanObject;

import lombok.Data;

@Data
public class CarBean {

    private String name;

    public CarBean(String name) {
        this.name = name;
    }
}
