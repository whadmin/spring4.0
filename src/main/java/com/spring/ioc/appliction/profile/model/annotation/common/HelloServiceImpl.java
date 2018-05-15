package com.spring.ioc.appliction.profile.model.annotation.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.spring.ioc.appliction.profile.model.HelloService;
import com.spring.ioc.appliction.profile.model.HelloService2;



@Component
public class HelloServiceImpl implements HelloService2 {
	
	@Value("${book.name}")
	public String name;

	public void setName(String name) {
		this.name = name;
	}

	public String sayHello() {
		return String.format("hello,I'm %s,this is a common environment!",
				name);
	}

}
