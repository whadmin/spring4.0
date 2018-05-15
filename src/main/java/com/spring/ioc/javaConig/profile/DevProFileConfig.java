package com.spring.ioc.javaConig.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.spring.ioc.appliction.profile.model.DevHelloService;
import com.spring.ioc.appliction.profile.model.HelloService;


@Configuration
@Profile("dev")
@PropertySource("classpath:profile/dev.properties")
public class DevProFileConfig {
	

	@Value("${book.name}") 
	public String name;
	
	@Bean
	public HelloService devHelloService() {
		DevHelloService devHelloService=new DevHelloService();
		devHelloService.setName(name);
		 
		 return devHelloService;
	}

}
