package com.spring.ioc.bean.getBean.populateBean.beanObject.annotation;

import com.spring.ioc.bean.getBean.populateBean.beanObject.qualifier.DataBase;
import com.spring.ioc.bean.getBean.populateBean.beanObject.qualifier.DataSourceType;
import com.spring.ioc.bean.getBean.populateBean.beanObject.qualifier.Mysql;
import com.spring.ioc.bean.getBean.populateBean.beanObject.qualifier.Oracle;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 17:28
 */
@ToString
@Getter
public class AutowireByAnnQualifierBean {

    @Autowired
    @Qualifier("mysqlDataSource")
    private DataSource mysqlDataSource1;

    @Autowired
    @DataSourceType(ip = "localhost", database = DataBase.MYSQL)
    private DataSource mysqlDataSource2;

    @Autowired
    @Mysql
    private DataSource mysqlDataSource3;

    @Autowired
    @Qualifier("oracleDataSource")
    private DataSource oracleDataSource1;

    @Autowired
    @DataSourceType(ip = "localhost", database = DataBase.ORACLE)
    private DataSource oracleDataSource2;

    @Autowired
    @Oracle
    private DataSource oracleDataSource3;
}
