package com.spring.ioc.model;

import java.io.IOException;

public class DependentBean {
    
    ResourceBean resourceBean;
    
    public void write(String ss) throws IOException {
        System.out.println("DependentBean:=========写资源");
    }

    public void init() throws IOException {
        System.out.println("DependentBean:=========初始化");
    }

    public void destroy() throws IOException {
        System.out.println("DependentBean:=========销毁");
    }
    
    public void setResourceBean(ResourceBean resourceBean) {
        this.resourceBean = resourceBean;
    }
}
