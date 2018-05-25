package com.spring.ioc.javaConig.propertySource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;


@Configuration
@Import({ EnvironmentGetConfig.class, ValueGetConfig.class,ValueOtherConfig.class })
@PropertySource("classpath:propertySource/application.properties")
@ComponentScan("com.spring.ioc.appliction.propertySource")
@EnableConfigurationProperties
public class PropertySourceConfig {

}
