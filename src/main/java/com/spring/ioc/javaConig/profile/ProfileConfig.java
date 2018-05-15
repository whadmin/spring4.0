package com.spring.ioc.javaConig.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import com.spring.ioc.appliction.profile.model.HelloService2;
import com.spring.ioc.appliction.profile.model.HelloServiceImpl2;

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
//	@Profile("produce")
//	public HelloService produceHelloService() {
//		return new ProduceHelloService();
//	}

	@Bean
	public HelloService2 helloService() {
		return new HelloServiceImpl2();
	}
}
