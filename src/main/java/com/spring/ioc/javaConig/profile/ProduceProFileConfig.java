package com.spring.ioc.javaConig.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import com.spring.ioc.appliction.profile.model.HelloService;
import com.spring.ioc.appliction.profile.model.ProduceHelloService;

@Configuration
@Profile("produce")
@PropertySource("classpath:profile/produce.properties")
public class ProduceProFileConfig {

	@Value("${book.name}")
	public String name;

	@Bean
	public HelloService produceHelloService() {
		ProduceHelloService produceHelloService = new ProduceHelloService();
		produceHelloService.setName(name);
		return produceHelloService;
	}

}
