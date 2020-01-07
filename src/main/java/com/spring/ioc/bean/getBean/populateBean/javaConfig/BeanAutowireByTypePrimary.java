package com.spring.ioc.bean.getBean.populateBean.javaConfig;

import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.AutowirebyTypeBean;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 22:10
 */
@Configuration
public class BeanAutowireByTypePrimary {

    @Bean(name = "bean_byType_primary", autowire = Autowire.BY_TYPE)
    public AutowirebyTypeBean bean_byName() {
        return new AutowirebyTypeBean();
    }

    @Bean(name = "mysqlDataSource")
    @Primary
    public DriverManagerDataSource mysqlDataSource() {
        return new DriverManagerDataSource();
    }

    @Bean(name = "oracleDataSource")
    public DriverManagerDataSource oracleDataSource() {
        return new DriverManagerDataSource();
    }
}
