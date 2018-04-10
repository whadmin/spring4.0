package com.spring.profile.bean;

import org.springframework.beans.factory.annotation.Value;

import com.spring.profile.bean.HelloService;

public class ProduceHelloService implements HelloService {

	// 这个值读取生产环境下的配置注入
	@Value("#{config.name}")
	private String name;

	public String sayHello() {
		return String.format("hello,I'm %s,this is a produce environment!",
				name);
	}
}