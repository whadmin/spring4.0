package com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation;

import lombok.Data;

/**
 * @Author: wuhao.w
 * @Date: 2020/7/4 17:46
 */
@Data
public class BeanObject {

    private String message;

    public BeanObject() {
        this.message = "Hello World!";
    }

    public BeanObject(String message) {
        this.message = message;
    }

    public void destroy() {
        System.out.println("destroy");
    }

    public void init() {
        System.out.println("init");
    }
}
