/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.spring.ioc.appliction.event;
import com.spring.ioc.appliction.event.event.CustomizeEvent;
import com.spring.ioc.appliction.event.event.GenericTestEvent;
import com.spring.ioc.appliction.event.listener.StringEventListener;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.ResolvableType;

import java.io.IOException;


public class SpringEventForXml {

	public static class TestEvents {

		public ApplicationEvent applicationEvent;

		public GenericTestEvent<?> wildcardEvent;

		public GenericTestEvent<String> stringEvent;

		public GenericTestEvent<Long> longEvent;

		public GenericTestEvent<IllegalStateException> illegalStateExceptionEvent;

		public GenericTestEvent<IOException> ioExceptionEvent;
	}

	protected ResolvableType getGenericApplicationEventType(String fieldName) {
		try {
			return ResolvableType.forField(TestEvents.class.getField(fieldName));
		}
		catch (NoSuchFieldException ex) {
			throw new IllegalStateException("No such field on Events '" + fieldName + "'");
		}
	}

	public ResolvableType resolveDeclaredEventType(Class<?> listenerType) {
		ResolvableType resolvableType = ResolvableType.forClass(listenerType).as(ApplicationListener.class);
		return (resolvableType.hasGenerics() ? resolvableType.getGeneric() : null);
	}

	/**
	 * 通过 CustomizeApplicationListener implements ApplicationListener<CustomizeEvent>
	 * 指定事件类型
	 *
	 * 通过 CustomizeGenericApplicationListener implements GenericApplicationListener 重写 supportsEventType
	 * 指定事件类型
	 */
	@Test
	public void test() throws NoSuchFieldException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:ioc/application/event/spring-event.xml");
		ApplicationEventMulticaster eventMulticaster = (ApplicationEventMulticaster)context.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME);
		eventMulticaster.multicastEvent(new CustomizeEvent(context));
	}


	/**
	 * 不指定事件类型
	 *
	 * 发布 new GenericTestEvent<>(this, "string")事件且不指定GenericTestEvent<String>:
	 *
	 * 1 事件类型通过，事件对象 new GenericTestEvent<>(this, "string")事件被解析为 GenericTestEvent<?>
	 * 2 能够匹配 WildcardEventListener implements ApplicationListener<GenericTestEvent<?>>
	 * 3 无法匹配 StringEventListener implements ApplicationListener<GenericTestEvent<String>>
	 */
	@Test
	public void test1() throws NoSuchFieldException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:ioc/application/event/spring-event.xml");
		ApplicationEventMulticaster eventMulticaster = (ApplicationEventMulticaster)context.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME);
		eventMulticaster.multicastEvent(new GenericTestEvent<>(context, "string"));
		eventMulticaster.multicastEvent(new GenericTestEvent<>(context, "longEvent"));

		//获取发送事件类型
		ResolvableType eventType = ResolvableType.forInstance(new GenericTestEvent<>(this, "string"));
		//com.spring.ioc.appliction.event_new.event.GenericTestEvent<?>
		System.out.println(eventType);

		//获取监听器支持事件类型
		ResolvableType declaredEventType = resolveDeclaredEventType(StringEventListener.class);
		System.out.println(declaredEventType);

		System.out.println(declaredEventType.isAssignableFrom(eventType));

	}




	/**
	 * 指定发布事件类型
	 *
	 * 发布 new GenericTestEvent<>(this, "string")事件且指定GenericTestEvent<String>:
	 *
	 * 1 能够匹配 WildcardEventListener implements ApplicationListener<GenericTestEvent<?>>
	 * 2 能够匹配 StringEventListener implements ApplicationListener<GenericTestEvent<String>>
	 */
	@Test
	public void test2() throws NoSuchFieldException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:ioc/application/event/spring-event.xml");
		ApplicationEventMulticaster eventMulticaster = (ApplicationEventMulticaster)context.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME);
		eventMulticaster.multicastEvent(new GenericTestEvent<>(context, "string"),getGenericApplicationEventType("stringEvent"));
		eventMulticaster.multicastEvent(new GenericTestEvent<>(context, 123L),getGenericApplicationEventType("longEvent"));

		//获取监听器支持事件类型
		ResolvableType declaredEventType = resolveDeclaredEventType(StringEventListener.class);
		System.out.println(declaredEventType);

		System.out.println(declaredEventType.isAssignableFrom(getGenericApplicationEventType("stringEvent")));
	}


}
