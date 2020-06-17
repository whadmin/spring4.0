package com.spring.ioc.appliction.environment;

//import javax.activation.DataSource;

import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.spring.ioc.javaConig.propertySource.PropertySourceConfig;
import com.spring.ioc.javaConig.propertySource.ValueOtherConfig;

public class SpringProperSourceJavaConfig {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				PropertySourceConfig.class);
		System.out.println(context.getEnvironment());
		ValueOtherConfig resourceService = (ValueOtherConfig) context
				.getBean(ValueOtherConfig.class);
		resourceService.outputResource();

		DataSource writeDataSource1 = context.getBean("writeDataSource1",
				DataSource.class);
		System.out.println(writeDataSource1);

		DataSource writeDataSource2 = context.getBean("writeDataSource2",
				DataSource.class);
		System.out.println(writeDataSource2);

		DataSource readDataSource1 = context.getBean("readDataSource1",
				DataSource.class);
		System.out.println(writeDataSource1);

		DataSource readDataSource2 = context.getBean("readDataSource2",
				DataSource.class);
		System.out.println(writeDataSource2);
		context.close();
	}

}
