/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.spring.ioc.appliction.event;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.ioc.appliction.event.model.ContentEvent;
import com.spring.ioc.appliction.event.model.annotation.Blog;
import com.spring.ioc.appliction.event.model.annotation.BlogModifiedEvent;

public class SpringEventForXml {

	@Test
	public void test1() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:event/spring-event-annotation1.xml");
		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
		context.publishEvent(new BlogModifiedEvent(new Blog(), true));
	}
	
	@Test
	public void test2() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:event/spring-event-annotation2.xml");
		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = context.getBeanFactory().getBeanDefinition(beanDefinitionName);
			System.out.println(beanDefinition);
		}
		context.publishEvent(new BlogModifiedEvent(new Blog(), true));
	}
}
