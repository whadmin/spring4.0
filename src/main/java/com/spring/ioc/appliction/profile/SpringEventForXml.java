/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.spring.ioc.appliction.profile;

import com.spring.ioc.appliction.event.model.annotation.Blog;
import com.spring.ioc.appliction.event.model.annotation.BlogModifiedEvent;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class SpringEventForXml {

	@Test
	public void test1() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:event/spring-event-annotation1.xml");
		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
		context.publishEvent(new BlogModifiedEvent(new Blog(), true));
	}
	

}
