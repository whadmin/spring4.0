package com.spring.ioc.appliction.profile.model;

import org.springframework.beans.factory.annotation.Value;

public class HelloServiceImpl implements HelloService {
	
	@Value("#{common.name}")
	private String name;

	public String sayHello() {
		return String.format("hello,I'm %s,this is a common environment!",
				name);
	}

}
