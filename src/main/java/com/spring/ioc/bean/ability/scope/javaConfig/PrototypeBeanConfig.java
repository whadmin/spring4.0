package com.spring.ioc.bean.ability.scope.javaConfig;

import com.spring.ioc.bean.ability.scope.beanObject.no_annotation.PrototypeBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @Author: wuhao.w
 * @Date: 2020/6/17 11:35
 */
@Configuration
public class PrototypeBeanConfig {

    @Bean
    @Scope(value = "prototype")
    public PrototypeBean prototypeBean() {
        return new PrototypeBean();
    }


}
