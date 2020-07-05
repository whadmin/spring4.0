package com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation;

import javax.sql.DataSource;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;

import com.spring.ioc.bean.getBean.populateBean.beanObject.qualifier.DataBase;
import com.spring.ioc.bean.getBean.populateBean.beanObject.qualifier.DataSourceType;
import com.spring.ioc.bean.getBean.populateBean.beanObject.qualifier.Mysql;

@Getter
public class HelloApiDecorator implements HelloApiDataSource {


    private String message;

    private DataSource dataSource;

    private DataSource dataSource1;

    private DataSource dataSource2;

    public HelloApiDecorator() {
    }

    public void setDataSource(
            @Qualifier("mysqlDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource1(@Mysql DataSource dataSource1) {
        this.dataSource1 = dataSource1;
    }

    public void setDataSource2(
            @DataSourceType(ip = "localhost", database = DataBase.MYSQL) DataSource dataSource2) {
        this.dataSource2 = dataSource2;
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }

    @Override
    public DataSource getDataSource1() {
        return this.dataSource1;
    }

    @Override
    public DataSource getDataSource2() {
        return this.dataSource2;
    }
}
