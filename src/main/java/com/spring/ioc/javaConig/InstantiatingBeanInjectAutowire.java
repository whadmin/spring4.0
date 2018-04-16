package com.spring.ioc.javaConig;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.HelloApiDataSource;
import com.spring.ioc.model.HelloApiDecorator;
import com.spring.ioc.model.HelloImpl;
import com.spring.ioc.model.ListBean;
import com.spring.ioc.model.qualifier.Mysql;

@Configuration
public class InstantiatingBeanInjectAutowire {
	
	@Bean
	@Primary
	public HelloApi helloApi() {
		return new HelloImpl();
	}
	
	@Bean
	public HelloApi HelloImpl() {
		return new HelloImpl();
	}
	
	@Bean(autowire=Autowire.BY_NAME)
	public HelloApiDataSource bean_byName() {
		return new HelloApiDecorator();
	}
	
	@Bean(autowire=Autowire.BY_TYPE)
	public HelloApiDataSource bean_byType() {
		return new HelloApiDecorator();
	}
	
	@Bean(autowire=Autowire.BY_TYPE)
	public ListBean listBean() {
		return new ListBean();
	}
	
	@Bean
	@Qualifier(value="mysqlDataSource")
	public DriverManagerDataSource mysqlDataSourceBean(){
		return new DriverManagerDataSource();	
	}
	
	@Bean
	@Qualifier(value="oracleDataSource")
	public DriverManagerDataSource oracleDataSourceBean(){
		return new DriverManagerDataSource();
		
	}
	
	@Bean
	public String message() {
		return "Hello World!";
	}
	

}
