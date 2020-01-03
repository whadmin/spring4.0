package com.spring.ioc.bean.getBean.propertyInjection.beanObject.annotation;

import com.spring.ioc.bean.getBean.propertyInjection.beanObject.qualifier.DataBase;
import com.spring.ioc.bean.getBean.propertyInjection.beanObject.qualifier.DataSourceType;
import com.spring.ioc.bean.getBean.propertyInjection.beanObject.qualifier.Mysql;
import com.spring.ioc.bean.getBean.propertyInjection.beanObject.qualifier.Oracle;
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
public class AutowireByAnnQualifierBean2 {

    @Autowired
    @Qualifier("mysqlDataSource")
    private DataSource mysqlDataSource;

    @Autowired
    @Qualifier("oracleDataSource")
    private DataSource oracleDataSource;

}
