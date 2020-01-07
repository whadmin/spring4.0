package com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation;

import lombok.ToString;

@ToString
public class IdRefTestBean {
    
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
