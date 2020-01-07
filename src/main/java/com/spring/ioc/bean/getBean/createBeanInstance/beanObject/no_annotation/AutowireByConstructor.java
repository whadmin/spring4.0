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
public class AutowireByConstructor {

    private String message;

    private DataSource mysqlDataSource;

    private DataSource oracleDataSource;

    public AutowireByConstructor(String message, DataSource mysqlDataSource, DataSource oracleDataSource) {
        this.message = message;
        this.mysqlDataSource = mysqlDataSource;
        this.oracleDataSource = oracleDataSource;
    }
}
