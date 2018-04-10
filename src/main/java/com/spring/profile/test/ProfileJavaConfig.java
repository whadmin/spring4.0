package com.spring.profile.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.spring.profile.bean.HelloService;
import com.spring.profile.javaconfig.ProfileConfig;
import com.spring.profile.javaconfig.annotation.ProfileConfigAnnotation;

public class ProfileJavaConfig {


	
	@Test
	public void testProfileConfig() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		//context.getEnvironment().setActiveProfiles("dev");
		context.register(ProfileConfig.class);
		context.refresh();

		HelloService helloService = context.getBean(HelloService.class);
		System.out.println(helloService.sayHello());

		context.getEnvironment().setActiveProfiles("produce");
		helloService = context.getBean(HelloService.class);
		System.out.println(helloService.sayHello());

		context.getEnvironment().setActiveProfiles(null);
		helloService = context.getBean(HelloService.class);
		System.out.println(helloService.sayHello());

		context.close();

	}
	
	@Test
    public void testProfileConfigAnnotation() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		context.getEnvironment().setActiveProfiles("dev"); // ①
		context.register(ProfileConfigAnnotation.class);// ②
		context.refresh(); // ③

		HelloService helloService = context.getBean(HelloService.class);
		System.out.println(helloService.sayHello());

		context.close();
		
	}

}
