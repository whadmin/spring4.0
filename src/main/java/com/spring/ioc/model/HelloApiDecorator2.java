package com.spring.ioc.model;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.spring.ioc.bean.getBean.populateBean.beanObject.qualifier.DataBase;
import com.spring.ioc.bean.getBean.populateBean.beanObject.qualifier.DataSourceType;
import com.spring.ioc.bean.getBean.populateBean.beanObject.qualifier.Mysql;

public class HelloApiDecorator2 implements HelloApiDataSource {

	@Autowired
	private HelloApi helloApi;

	@Resource
	private String message;

	@Autowired
	@Qualifier("mysqlDataSource")
	private DataSource dataSource;

	@Autowired(required = false)
	@Mysql
	private DataSource dataSource1;

	@Autowired(required = false)
	@DataSourceType(ip = "localhost", database = DataBase.MYSQL)
	private DataSource dataSource2;

	@Autowired(required = false)
	private String isnull;

	public HelloApiDecorator2() {
	}

	public HelloApiDecorator2(HelloApi helloApi) {
		this.helloApi = helloApi;
	}

	@Override
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

	@Override
	public HelloApi getHelloApi() {
		return helloApi;
	}
}
