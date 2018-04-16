package com.spring.ioc.model;

import javax.sql.DataSource;

public interface HelloApiDataSource {

	public DataSource getDataSource();

	public DataSource getDataSource1();

	public DataSource getDataSource2();

	public HelloApi getHelloApi();

	public String getMessage();
}
