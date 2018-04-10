package com.spring.profile.javaconfig.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({ DevProFileConfigAnnotation.class,
		ProduceProFileConfigAnnotation.class })
@PropertySource("classpath:profile/common.properties")
public class ProfileConfigAnnotation {

}
