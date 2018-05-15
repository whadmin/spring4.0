package com.spring.ioc.javaConig.profile.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "com.spring.ioc.appliction.profile.model.annotation.common" })
@Import({ DevProFileConfigAnnotation.class,
		ProduceProFileConfigAnnotation.class })
@PropertySource("classpath:profile/common.properties")
public class ProfileConfigAnnotation {

}
