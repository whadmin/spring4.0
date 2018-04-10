package com.spring.profile.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.spring.profile.bean.DevHelloService;
import com.spring.profile.bean.HelloService;
import com.spring.profile.bean.HelloService2;
import com.spring.profile.bean.HelloServiceImpl;
import com.spring.profile.bean.HelloServiceImpl2;
import com.spring.profile.bean.ProduceHelloService;

@Configuration
@Import({ DevProFileConfig.class, ProduceProFileConfig.class })
@PropertySource("classpath:profile/common.properties")
public class ProfileConfig {

//	@Bean
//	@Profile("dev")
//	public HelloService devHelloService() {
//		return new DevHelloService();
//	}
//
//	@Bean
//	@Profile("prod")
//	public HelloService produceHelloService() {
//		return new ProduceHelloService();
//	}

	@Bean
	public HelloService2 helloService() {
		return new HelloServiceImpl2();
	}
}
