package com.spring.ioc.javaConig;

import java.lang.annotation.ElementType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.ioc.model.DependencyInjectByInstanceFactory;
import com.spring.ioc.model.DependencyInjectByStaticFactory;
import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.HelloImpl;
import com.spring.ioc.model.HelloImpl3;
import com.spring.ioc.model.HelloImpl4;

@Configuration
public class InstantiatingBean {
    
    @Bean(initMethod="init",destroyMethod="destroy")
    public HelloApi bean1() {
        return new HelloImpl();
    }
    
    @Bean
    public HelloApi bean2() {
        return new HelloImpl3("Hello Spring!",1);
    }
    
    @Bean
    public HelloApi bean3() {
        return DependencyInjectByStaticFactory.newInstance("Hello Spring!",2);
    }
    
    @Bean
    public HelloApi bean4() {
        return new DependencyInjectByInstanceFactory().newInstance("Hello Spring!",3);
    }
    
    @Bean
    public HelloApi bean5() {
    	HelloImpl4 helloImpl4= new HelloImpl4();
    	helloImpl4.setMessage("Hello World!");
    	helloImpl4.setIndex(1);
    	return helloImpl4;
    }

}
