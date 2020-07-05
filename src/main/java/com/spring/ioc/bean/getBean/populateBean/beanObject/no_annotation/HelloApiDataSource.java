package com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation;

import javax.sql.DataSource;

public interface HelloApiDataSource {

	public DataSource getDataSource();

	public DataSource getDataSource1();

	public DataSource getDataSource2();

	public String getMessage();
}
