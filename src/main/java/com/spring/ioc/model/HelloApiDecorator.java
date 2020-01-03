package com.spring.ioc.model;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;

import com.spring.ioc.bean.getBean.propertyInjection.beanObject.qualifier.DataBase;
import com.spring.ioc.bean.getBean.propertyInjection.beanObject.qualifier.DataSourceType;
import com.spring.ioc.bean.getBean.propertyInjection.beanObject.qualifier.Mysql;
import com.spring.ioc.bean.getBean.propertyInjection.beanObject.qualifier.Oracle;

public class HelloApiDecorator implements HelloApiDataSource {

	private HelloApi helloApi;

	private String message;

	private DataSource dataSource;

	private DataSource dataSource1;

	private DataSource dataSource2;

	public HelloApiDecorator() {
	}

	public HelloApiDecorator(HelloApi helloApi) {
		this.helloApi = helloApi;
	}

	public void setDataSource(
			@Qualifier("mysqlDataSource") DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setDataSource1(@Mysql DataSource dataSource1) {
		this.dataSource1 = dataSource1;
	}

	public void setDataSource2(
			@DataSourceType(ip = "localhost", database = DataBase.MYSQL) DataSource dataSource2) {
		this.dataSource2 = dataSource2;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setHelloApi(HelloApi helloApi) {
		this.helloApi = helloApi;
	}
	
	public HelloApi getHelloApi() {
		return helloApi;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public DataSource getDataSource() {
		return this.dataSource;
	}

	@Override
	public DataSource getDataSource1() {
		return this.dataSource1;
	}

	@Override
	public DataSource getDataSource2() {
		return this.dataSource2;
	}
}
