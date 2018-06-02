package com.spring.ioc.appliction.profile.model;

import org.springframework.beans.factory.annotation.Value;



public class ProduceHelloService implements HelloService {

	// 这个值读取生产环境下的配置注入
	public String name;

	@Value("${book.name}")
	public String name1;

	public void setName(String name) {
		this.name = name;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String sayHello() {
		return String.format("hello,I'm %s,this is a produce environment!",
				name);
	}
}