package com.spring.ioc.bean.assemblyBean.javaConfig.profile;

import com.spring.ioc.bean.assemblyBean.beanObject.profile.HelloService;
import com.spring.ioc.bean.assemblyBean.beanObject.profile.ProduceHelloServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("produce")
@PropertySource("classpath:ioc/application/environment/produce.properties")
public class ProduceProFileConfig {

	@Bean
	public HelloService produceHelloService() {
		return new ProduceHelloServiceImpl();
	}

}
