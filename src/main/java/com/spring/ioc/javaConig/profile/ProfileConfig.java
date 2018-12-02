package com.spring.ioc.javaConig.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import com.spring.ioc.appliction.profile.model.CommonService;
import com.spring.ioc.appliction.profile.model.CommonServiceImpl;

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
	public CommonService helloService() {
		return new CommonServiceImpl();
	}
}
