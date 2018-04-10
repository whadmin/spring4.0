package com.spring.profile.bean.annotation.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.spring.profile.bean.HelloService;

@Component
public class HelloServiceImpl implements HelloService {
	
	@Value("#{common.name}")
	private String name;

	public String sayHello() {
		return String.format("hello,I'm %s,this is a common environment!",
				name);
	}

}
