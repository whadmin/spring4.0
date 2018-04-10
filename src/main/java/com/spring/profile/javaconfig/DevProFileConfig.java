package com.spring.profile.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.spring.profile.bean.DevHelloService;
import com.spring.profile.bean.HelloService;
@Configuration
@Profile("dev")
@PropertySource("classpath:profile/dev.properties")
public class DevProFileConfig {
	
	@Bean
	public HelloService devHelloService() {
		return new DevHelloService();
	}

}
