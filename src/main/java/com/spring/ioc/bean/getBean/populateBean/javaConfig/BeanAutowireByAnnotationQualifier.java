package com.spring.ioc.bean.getBean.populateBean.javaConfig;

import com.spring.ioc.bean.getBean.populateBean.beanObject.annotation.AutowireByAnnQualifierBean2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 22:20
 */
@Configuration
public class BeanAutowireByAnnotationQualifier {

    @Bean(name = "bean_byType_qualifier")
    public AutowireByAnnQualifierBean2 bean_byType_qualifier() {
        return new AutowireByAnnQualifierBean2();
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
