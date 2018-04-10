package com.spring.profile.javaconfig.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "com.spring.profile.bean.annotation.dev" })
@Profile("dev")
@PropertySource("classpath:profile/dev.properties")
public class DevProFileConfigAnnotation {

}
