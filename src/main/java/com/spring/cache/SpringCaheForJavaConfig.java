package com.spring.cache;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.cache.javaConfig.RedisConfig;
import com.spring.cache.service.AccountService;
import com.spring.cache.service.annotation.AnnotationAccountService;
import com.spring.ioc.javaConig.ConditionConifg;

public class SpringCaheForJavaConfig {
	@Test
	public void springCacheForJavaConfig() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RedisConfig.class);

		AnnotationAccountService s = (AnnotationAccountService) context
				.getBean("annotationAccountService");
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


}
