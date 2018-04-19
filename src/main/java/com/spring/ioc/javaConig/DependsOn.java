package com.spring.ioc.javaConig;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.spring.ioc.model.DependentBean;
import com.spring.ioc.model.ResourceBean;

@Configuration
public class DependsOn {

	@Bean
	public String file() {
		return "D:/test.txt";
	}

	@Bean(autowire = Autowire.BY_NAME,initMethod="init",destroyMethod="destroy")
	@Lazy(true)
	public ResourceBean resourceBean() {
		ResourceBean resourceBean = new ResourceBean();
		return resourceBean;
	}

	@Bean(autowire = Autowire.BY_NAME,initMethod="init",destroyMethod="destroy")
	@org.springframework.context.annotation.DependsOn(value={"resourceBean"})
	public DependentBean dependentBean() {
		DependentBean dependentBean = new DependentBean();
		return dependentBean;
	}

}
