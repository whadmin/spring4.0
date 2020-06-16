package com.spring.ioc.javaConig;

import com.spring.ioc.bean.ability.scope.beanObject.no_annotation.SingletonBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class InstantiatingBeanScope {
	
	@Bean
	@Scope
	public SingletonBean prototypePrinter() {
		return new SingletonBean();
	}

	@Bean
	public SingletonBean singletonPrinter() {
		return new SingletonBean();
	}

}
