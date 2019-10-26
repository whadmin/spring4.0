package com.spring.ioc.bean.register.beanObject.configuration;

import com.spring.ioc.bean.register.beanObject.BeanObject;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(value ="configurationConfig")
@Import(OtherConfigurationConfig.class)
public class ConfigurationConfig {

    @Bean(name = "bean",autowire = Autowire.NO, destroyMethod ="destroy", initMethod ="init")
    public BeanObject BeanObject() {
        return new BeanObject();
    }
}
