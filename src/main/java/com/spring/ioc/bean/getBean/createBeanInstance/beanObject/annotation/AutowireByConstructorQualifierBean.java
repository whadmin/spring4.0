package com.spring.ioc.bean.getBean.createBeanInstance.beanObject.annotation;

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
public class AutowireByConstructorQualifierBean {

    public AutowireByConstructorQualifierBean( @Qualifier("mysqlDataSource") DataSource mysqlDataSource,  @Qualifier("oracleDataSource")DataSource oracleDataSource){
       this.mysqlDataSource=mysqlDataSource;
       this.oracleDataSource=oracleDataSource;
    }

    private DataSource mysqlDataSource;

    private DataSource oracleDataSource;
}
