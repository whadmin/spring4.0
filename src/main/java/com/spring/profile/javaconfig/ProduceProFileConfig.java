package com.spring.profile.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.spring.profile.bean.HelloService;
import com.spring.profile.bean.ProduceHelloService;

@Configuration
@Profile("produce")
@PropertySource("classpath:profile/produce.properties")
public class ProduceProFileConfig {

	@Bean
	public HelloService produceHelloService() {
		return new ProduceHelloService();
	}

}
