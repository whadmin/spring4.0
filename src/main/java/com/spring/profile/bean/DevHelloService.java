package com.spring.profile.bean;

import org.springframework.beans.factory.annotation.Value;

public class DevHelloService implements HelloService {

	// 这个值是读取开发环境下的配置文件注入
	@Value("#{config.name}")
	private String name;

	public String sayHello() {
		return String.format("hello,I'm %s,this is a development environment!",
				name);
	}
}
