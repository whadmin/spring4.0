package com.spring.ioc.appliction.profile;

import java.util.Arrays;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.appliction.profile.model.HelloService;
import com.spring.ioc.appliction.profile.model.CommonService;

public class ProfileForXml {

	/**
	 * -Dspring.profiles.active="dev"
	 */
	@Test
	public void test1() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:profile/spring-profile-config.xml");

		
		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
		System.out.println(context.getEnvironment());

		CommonService commonService = context.getBean(CommonService.class);
		System.out.println(commonService.sayHello());

		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
		HelloService helloService = context.getBean(HelloService.class);
		System.out.println(helloService.sayHello());



	}

}
