package com.spring.ioc.javaConig.profile.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "com.spring.ioc.appliction.profile.model.annotation.dev" })
@Profile("dev")
@PropertySource("classpath:profile/dev.properties")
public class DevProFileConfigAnnotation {

}
