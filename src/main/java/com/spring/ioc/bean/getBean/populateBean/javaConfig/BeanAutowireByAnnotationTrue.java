package com.spring.ioc.bean.getBean.populateBean.javaConfig;

import com.spring.ioc.bean.getBean.populateBean.beanObject.annotation.AutowireByAnnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/3 22:19
 */
@Configuration
public class BeanAutowireByAnnotationTrue {

    @Bean(name = "bean_annotation")
    public AutowireByAnnBean bean_annotation() {
        return new AutowireByAnnBean();
    }

    @Bean(name = "mysqlDataSource")
    public DriverManagerDataSource mysqlDataSource() {
        return new DriverManagerDataSource();
    }
}
