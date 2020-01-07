package com.spring.ioc.bean.getBean.populateBean.javaConfig;

import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowireByNameBean;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 21:48
 */
@Configuration
public class BeanAutowireByName {

    @Bean(name = "bean_byName", autowire = Autowire.BY_NAME)
    public AutowireByNameBean bean_byName() {
        return new AutowireByNameBean();
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
