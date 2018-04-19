package com.spring.ioc.javaConig;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.HelloApiDataSource;
import com.spring.ioc.model.HelloApiDecorator;
import com.spring.ioc.model.HelloImpl;

@Configuration
public class DependencyCheckAll {

	@Bean(autowire = Autowire.BY_NAME)
	public HelloApiDataSource bean() {
		return new HelloApiDecorator();
	}

	@Bean
	@Primary
	public HelloApi helloApi() {
		return new HelloImpl();
	}

	@Bean
	public DriverManagerDataSource dataSource() {
		return new DriverManagerDataSource();
	}
	@Bean
	public DriverManagerDataSource dataSource1() {
		return new DriverManagerDataSource();
	}
	@Bean
	public DriverManagerDataSource dataSource2() {
		return new DriverManagerDataSource();
	}
	
	@Bean
	public String message() {
		return "Hello World!";
	}
}
