package com.spring.ioc.bean.getBean.propertyInjection.beanObject.no_annotation;

public class BooleanTestBean {
    
    private boolean success;
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public boolean isSuccess() {
        return success;
    }
}
