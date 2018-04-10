package com.spring.ioc.javaConig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.spring.ioc.model.annotation" })
public class InstantiationContainerAnnotation {

}
