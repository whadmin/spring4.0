package com.spring.ioc.javaConig;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.HelloApiDecorator;
import com.spring.ioc.model.HelloImpl;
import com.spring.ioc.model.HelloImpl3;
import com.spring.ioc.model.HelloImpl4;
import com.spring.ioc.model.ListBean;

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
	
	@Bean
	public HelloApi helloApi() {
		return new HelloImpl();
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
	
	@Bean(autowire=Autowire.BY_NAME)
	public HelloApi bean_byName() {
		return new HelloApiDecorator();
	}
	
	@Bean(autowire=Autowire.BY_TYPE)
	public HelloApi bean_byType() {
		return new HelloApiDecorator();
	}
	
	@Bean(autowire=Autowire.BY_TYPE)
	public ListBean listBean() {
		return new ListBean();
	}
}
