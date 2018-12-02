package com.spring.ioc.appliction.profile.model.annotation.produce;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.spring.ioc.appliction.profile.model.HelloService;

@Component
//@Profile("produce")
public class ProduceHelloService implements HelloService {


	public String getDynamicName() {
		return dynamicName;
	}

	public void setDynamicName(String dynamicName) {
		this.dynamicName = dynamicName;
	}

	@Value("#{config.name}")
	public String dynamicName;

	@Override
	public String sayHello() {
		return String.format("hello,I'm %s,this is a produce environment!", dynamicName);
	}
}
