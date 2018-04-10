package com.spring.profile.bean.annotation.produce;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.spring.profile.bean.HelloService;

@Component
@Profile("produce")  
public class ProduceHelloService implements HelloService {

	// 这个值读取生产环境下的配置注入
	@Value("#{config.name}")
	private String name;

	public String sayHello() {
		return String.format("hello,I'm %s,this is a produce environment!",
				name);
	}
}