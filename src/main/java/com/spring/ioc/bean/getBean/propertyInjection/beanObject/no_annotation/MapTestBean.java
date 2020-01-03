package com.spring.ioc.bean.getBean.propertyInjection.beanObject.no_annotation;

import java.util.Map;

public class MapTestBean {
    
    private Map<String, String> values;

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    
}
