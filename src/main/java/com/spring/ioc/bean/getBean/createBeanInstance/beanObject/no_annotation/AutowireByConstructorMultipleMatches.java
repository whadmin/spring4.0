package com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation;

import lombok.Getter;
import lombok.ToString;

import javax.sql.DataSource;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 14:23
 */
@ToString
@Getter
public class AutowireByConstructorMultipleMatches {

    private String message;

    private DataSource dataSource;

    public AutowireByConstructorMultipleMatches(String message, DataSource dataSource) {
        this.message = message;
        this.dataSource = dataSource;
    }
}
