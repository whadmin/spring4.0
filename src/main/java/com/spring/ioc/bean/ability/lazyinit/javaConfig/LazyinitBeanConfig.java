package com.spring.ioc.bean.ability.lazyinit.javaConfig;

import com.spring.ioc.bean.ability.lazyinit.beanObject.no_annotation.LazyinitBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @Author: wuhao.w
 * @Date: 2020/6/17 12:30
 */
@Configuration
public class LazyinitBeanConfig {

    @Bean
    public LazyinitBean nolazyinit() {
        return new LazyinitBean();
    }

    @Bean
    @Lazy
    public LazyinitBean lazyinit() {
        return new LazyinitBean();
    }
}
