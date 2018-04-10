package com.spring.ioc.javaConig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.HelloImpl;

@Configuration
public class InstantiatContainer {

    @Bean
    public HelloApi hello() {
        return new HelloImpl();
    }
}
