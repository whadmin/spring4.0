package com.spring.profile.bean.annotation.dev;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.spring.profile.bean.HelloService;

@Component
@Profile(value = "dev") 
public class DevHelloService implements HelloService {

	// 这个值是读取开发环境下的配置文件注入
	@Value("#{config.name}")
	private String name;

	public String sayHello() {
		return String.format("hello,I'm %s,this is a development environment!",
				name);
	}
}
