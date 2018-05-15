package com.spring.ioc.appliction.profile;

import java.util.Arrays;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.appliction.profile.model.HelloService;
import com.spring.ioc.appliction.profile.model.HelloService2;
import com.spring.ioc.javaConig.profile.ProfileConfig;

public class ProfileForXml {

	/**
	 * -Dspring.profiles.active="dev"
	 */
	@Test
	public void test1() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:profile/spring-profile-config.xml");

		
		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
		System.out.println(context.getEnvironment());

		HelloService2 helloService2 = context.getBean(HelloService2.class);
		System.out.println(helloService2.sayHello());

		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
		HelloService helloService = context.getBean(HelloService.class);
		System.out.println(helloService.sayHello());



	}

}
