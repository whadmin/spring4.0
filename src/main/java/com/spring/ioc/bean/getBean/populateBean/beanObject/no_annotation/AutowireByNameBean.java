package com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation;

import lombok.Getter;
import lombok.ToString;

import javax.sql.DataSource;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 14:23
 */
@ToString
@Getter
public class AutowireByNameBean {

    private String message;

    private DataSource mysqlDataSource;

    private DataSource oracleDataSource;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMysqlDataSource(DataSource mysqlDataSource) {
        this.mysqlDataSource = mysqlDataSource;
    }

    public void setOracleDataSource(DataSource oracleDataSource) {
        this.oracleDataSource = oracleDataSource;
    }
}
