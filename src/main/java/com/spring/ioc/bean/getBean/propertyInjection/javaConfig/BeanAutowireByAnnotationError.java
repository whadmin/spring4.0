package com.spring.ioc.bean.getBean.propertyInjection.javaConfig;

import com.spring.ioc.bean.getBean.propertyInjection.beanObject.annotation.AutowireByAnnBean;
import com.spring.ioc.bean.getBean.propertyInjection.beanObject.annotation.AutowireByAnnQualifierBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 22:20
 */
@Configuration
public class BeanAutowireByAnnotationError {

    @Bean(name = "bean_annotation")
    public AutowireByAnnBean bean_annotation() {
        return new AutowireByAnnBean();
    }

    @Bean(name = "mysqlDataSource")
    public DriverManagerDataSource mysqlDataSource() {
        return new DriverManagerDataSource();
    }

    @Bean(name = "oracleDataSource")
    public DriverManagerDataSource oracleDataSource() {
        return new DriverManagerDataSource();
    }
}
