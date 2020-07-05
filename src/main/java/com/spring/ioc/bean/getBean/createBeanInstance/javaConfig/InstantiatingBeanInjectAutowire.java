package com.spring.ioc.bean.getBean.createBeanInstance.javaConfig;

//实例化Bean自动注入
import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.HelloApiDataSource;
import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.HelloApiDecorator;
import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.HelloApiDecorator2;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.spring.ioc.bean.ability.alias.beanObject.AliasBeanObject;
import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.ListBean;

@Configuration
public class InstantiatingBeanInjectAutowire {
	
	@Bean
	@Primary
	public AliasBeanObject helloApi() {
		return new AliasBeanObject();
	}
	
	@Bean
	public AliasBeanObject HelloImpl() {
		return new AliasBeanObject();
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
	public HelloApiDataSource bean_Autowired() {
		 return new HelloApiDecorator2();
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
