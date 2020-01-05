//实例工厂类
package com.spring.ioc.bean.getBean.build.beanObject.factory;


import com.spring.ioc.bean.getBean.build.beanObject.no_annotation.ConstructorHaveParamBean;


public class DependencyInjectByInstanceFactory {
    
    public ConstructorHaveParamBean newInstance(String message) {
        return new ConstructorHaveParamBean(message);
    }
    
}
