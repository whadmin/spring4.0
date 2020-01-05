package com.spring.ioc.bean.getBean.build.javaConfig;

import com.spring.ioc.bean.getBean.build.beanObject.no_annotation.ConstructorNoParamBean;
import com.spring.ioc.bean.getBean.build.beanObject.no_annotation.ConstructorHaveParamBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationBean {


    @Bean(initMethod = "init", destroyMethod = "destroy")
    public ConstructorHaveParamBean buildBeanHaveParam(String message) {
        return new ConstructorHaveParamBean(message);
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public ConstructorNoParamBean buildBean() {
        return new ConstructorNoParamBean();
    }

    @Bean
    public String message() {
        return "Hello World!";
    }
}
