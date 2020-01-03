package com.spring.ioc.bean.ability.dependsOn.beanObject.no_annotation;


public class ResourceBean {
    

    public void init() {
        System.out.println("ResourceBean:========初始化");
    }
    
    public void destroy() {
        System.out.println("ResourceBean:========销毁");
    }
}
