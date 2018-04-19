package com.spring.ioc.bean.xml;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.model.HelloApiDataSource;

public class InstantiationBeanAnnotation {
	
	@Test
	public void testAutowireByName() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/instantiatingBeanInjectAutowire.xml");
		HelloApiDataSource helloApi = context.getBean("bean_byName", HelloApiDataSource.class);
		System.out.println(helloApi.getMessage());
		helloApi.getHelloApi().sayHello();
	}

}
