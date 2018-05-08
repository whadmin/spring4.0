/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.spring.ioc.appliction.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.ioc.appliction.event.model.ContentEvent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:event/spring-event-default.xml" })
public class eventDefaultForXml {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void testPublishEvent() {
		applicationContext.publishEvent(new ContentEvent("今年是龙年的博客更新了"));
	}
}
