package com.spring.ioc.javaConig.profile.annotation;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "com.spring.ioc.appliction.profile.model.annotation.produce" })
@Profile("produce")
@PropertySource("classpath:profile/produce.properties")
public class ProduceProFileConfigAnnotation {



}
