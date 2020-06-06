package com.spring.ioc.bean.assemblyBean.javaConfig.profile;

import com.spring.ioc.bean.assemblyBean.beanObject.profile.DevHelloServiceImpl;
import com.spring.ioc.bean.assemblyBean.beanObject.profile.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;


@Configuration
@Profile("dev")
@PropertySource("classpath:profile/dev.properties")
public class DevProFileConfig {
	
	@Bean
	public HelloService devHelloService() {
		return new DevHelloServiceImpl();
	}

}
