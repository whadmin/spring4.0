package com.spring.ioc.bean.getBean.propertyInjection.javaConfig;

import com.spring.ioc.bean.getBean.propertyInjection.beanObject.no_annotation.AutowirebyTypeBean;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 21:59
 */
@Configuration
public class BeanAutowireByType {

    @Bean(name = "bean_byType", autowire = Autowire.BY_TYPE)
    public AutowirebyTypeBean bean_byName() {
        return new AutowirebyTypeBean();
    }

    @Bean(name = "mysqlDataSource")
    public DriverManagerDataSource mysqlDataSource() {
        return new DriverManagerDataSource();
    }

}
