package com.spring.ioc.appliction.propertySource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	
	public static void main(String[] args) {
		 AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ElConfig.class);
		 
		 ElConfig resourceService = context.getBean(ElConfig.class);
		 
		 System.out.println(context.getEnvironment().getProperty("file.encoding"));
		 
System.out.println(System.getProperty("file.encoding"));
		 
		 resourceService.outputResource();
		 
		 context.close();
	}

}
