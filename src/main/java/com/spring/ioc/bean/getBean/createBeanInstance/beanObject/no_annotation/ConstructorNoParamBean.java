package com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation;

import lombok.Getter;
import lombok.ToString;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/5 16:22
 */
@ToString
@Getter
public class ConstructorNoParamBean {

    private String message;

    public ConstructorNoParamBean() {
        this.message = "Hello World!";
    }

    public void destroy() {
        System.out.println("destroy");
    }

    public void init() {
        System.out.println("init");
    }
}
