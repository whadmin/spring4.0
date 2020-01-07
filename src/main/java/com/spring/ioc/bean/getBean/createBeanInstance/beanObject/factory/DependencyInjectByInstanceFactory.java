//实例工厂类
package com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory;


import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorHaveParamBean;


public class DependencyInjectByInstanceFactory {
    
    public ConstructorHaveParamBean newInstance(String message) {
        return new ConstructorHaveParamBean(message);
    }
    
}
