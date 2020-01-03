package com.spring.ioc.javaConig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.HelloImpl3;

@Configuration
public class InstantiatingBean {

	@Bean
	public String message() {
		return "Hello World!";
	}

	@Bean
	public int index() {
		return 1;
	}
	


	@Bean(initMethod = "init", destroyMethod = "destroy")
	@Primary
	public HelloApi bean1() {
		return new HelloImpl3();
	}

	@Bean
	public HelloApi bean2(String message,int index) {
		return new HelloImpl3(message, index);
	}
}
