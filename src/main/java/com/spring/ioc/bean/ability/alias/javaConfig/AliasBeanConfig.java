package com.spring.ioc.bean.ability.alias.javaConfig;

import com.spring.ioc.bean.ability.alias.beanObject.AliasBeanObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.HelloApi2;
import com.spring.ioc.model.HelloImpl2;

@Configuration
public class AliasBeanConfig {

    @Bean
    public AliasBeanObject alias_bean1() {
        return new AliasBeanObject();
    }

    @Bean(name = {"alias_bean2"})
    public AliasBeanObject bean2() {
        return new AliasBeanObject();
    }

    @Bean(name = {"alias_bean3", "bean3_alias1", "bean3_alias2"})
    public AliasBeanObject bean3() {
        return new AliasBeanObject();
    }

    @Bean(name = {"alias_bean4", "bean4_alias1", "bean4_alias2"})
    public AliasBeanObject alias_bean4() {
        return new AliasBeanObject();
    }

}
