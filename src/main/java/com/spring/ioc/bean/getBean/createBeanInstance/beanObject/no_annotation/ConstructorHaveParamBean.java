package com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation;

import lombok.Getter;
import lombok.ToString;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/5 16:31
 */
@ToString
@Getter
public class ConstructorHaveParamBean {

    private String message;

    public ConstructorHaveParamBean(String message) {
        this.message = message;
    }

    public void destroy() {
        System.out.println("destroy");
    }

    public void init() {
        System.out.println("init");
    }
}
