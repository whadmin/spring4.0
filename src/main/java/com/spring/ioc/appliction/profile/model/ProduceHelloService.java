package com.spring.ioc.appliction.profile.model;

import org.springframework.beans.factory.annotation.Value;



public class ProduceHelloService implements HelloService {


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDynamicName() {
		return dynamicName;
	}

	public void setDynamicName(String dynamicName) {
		this.dynamicName = dynamicName;
	}

	/**
	 * DevProFileConfig,ProduceProFileConfig外部动态设置.
	 */
	public String name;

	@Value("${name}")
	public String dynamicName;

	@Override
	public String sayHello() {
		return String.format("hello,I'm %s,I'm %s,this is a development environment!", name,dynamicName);
	}
}
