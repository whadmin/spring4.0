package com.spring.ioc.bean.ability.scope.javaConfig;

import com.spring.ioc.bean.ability.scope.beanObject.no_annotation.SingletonBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * @Author: wuhao.w
 * @Date: 2020/6/17 11:35
 */
@Configuration
public class SingletonBeanConfig {

    @Bean
    @Scope(value = "singleton")
    public SingletonBean singletonBean() {
        return new SingletonBean();
    }

    @Bean
    @Scope(value = "singleton")
    @Lazy
    public SingletonBean singletonBean2() {
        return new SingletonBean();
    }
}
