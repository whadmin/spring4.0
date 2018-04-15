package com.spring.ioc.javaConig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.spring.ioc.model.Printer;

public class InstantiatingBeanScope {
	
	@Bean
	@Scope()
	public Printer prototypePrinter() {
		return new Printer();
	}

	@Bean
	public Printer singletonPrinter() {
		return new Printer();
	}

}
