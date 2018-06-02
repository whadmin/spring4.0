package com.springcache;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.springcache.service.AccountService;
import com.springcache.service.annotation.AnnotationAccountService;



public class SpringCaheForXml {
	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:cache/spring-cache-xml.xml");// 加载 spring 配置文件

		AccountService s = (AccountService) context
				.getBean("accountService");
		// 第一次查询，应该走数据库
		System.out.print("first query...");
		s.getAccountByName("somebody");
		// 第二次查询，应该不查数据库，直接返回缓存的值
		System.out.print("second query...");
		s.getAccountByName("somebody");
		System.out.println();
	}
	
	@Test
	public void test1() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:cache/spring-cache-annotation.xml");// 加载 spring 配置文件

		AnnotationAccountService s = (AnnotationAccountService) context
				.getBean("annotationAccountService");
		// 第一次查询，应该走数据库
		System.out.print("first query...");
		s.getAccountByName("somebody");
		// 第二次查询，应该不查数据库，直接返回缓存的值
		System.out.print("second query...");
		s.getAccountByName("somebody");
		System.out.println();
	}

}
