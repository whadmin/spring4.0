package com.spring.cache;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.cache.service.AccountService;
import com.spring.cache.service.annotation.AnnotationAccountService;

public class SpringCaheForXml {
	@Test
	public void springCacheForXml() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:cache/spring-cache-xml.xml");// 加载 spring 配置文件

		AccountService s = (AccountService) context
				.getBean("accountService");
		// 第一次查询，应该走数据库
		System.out.println("first query...");
		System.out.println(ReflectionToStringBuilder.toString(s.getAccountByName("somebody1"), ToStringStyle.MULTI_LINE_STYLE));
		
		// 第二次查询，应该不查数据库，直接返回缓存的值
		System.out.println("second query...");
		System.out.println(ReflectionToStringBuilder.toString(s.getAccountByName("somebody1"), ToStringStyle.MULTI_LINE_STYLE));
		//更新数据库 更新缓存
		s.updateAccountByName("somebody1");
		//获取新数据库
		System.out.println(ReflectionToStringBuilder.toString(s.getAccountByName("somebody1"), ToStringStyle.MULTI_LINE_STYLE));
		//删除数据
		s.deleteAccountByName("somebody1");
		
		System.out.println(ReflectionToStringBuilder.toString(s.getAccountByName("somebody1"), ToStringStyle.MULTI_LINE_STYLE));


	}
	
	@Test
	public void springCacheForAnnotation() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:cache/spring-cache-annotation.xml");// 加载 spring 配置文件

		AnnotationAccountService s = (AnnotationAccountService) context
				.getBean("annotationAccountService");
		// 第一次查询，应该走数据库
		System.out.println("first query...");
		System.out.println(ReflectionToStringBuilder.toString(s.getAccountByName("somebody3"), ToStringStyle.MULTI_LINE_STYLE));
		
		// 第二次查询，应该不查数据库，直接返回缓存的值
		System.out.println("second query...");
		System.out.println(ReflectionToStringBuilder.toString(s.getAccountByName("somebody3"), ToStringStyle.MULTI_LINE_STYLE));
		
		// 第三次查询，应该不查数据库，直接返回缓存的值
		System.out.println("three query...");
		System.out.println(ReflectionToStringBuilder.toString(s.getAccountByName1("somebody3"), ToStringStyle.MULTI_LINE_STYLE));
		//更新数据库 更新缓存
		s.updateAccountByName("somebody3");
		//获取新数据库
		System.out.println(ReflectionToStringBuilder.toString(s.getAccountByName("somebody3"), ToStringStyle.MULTI_LINE_STYLE));
		//删除数据
		s.deleteAccountByName("somebody3");
		
		System.out.println(ReflectionToStringBuilder.toString(s.getAccountByName("somebody3"), ToStringStyle.MULTI_LINE_STYLE));

	}

}
