package com.spring.ioc.bean.ability.dependsOn.beanObject.annotation;


import com.spring.ioc.bean.ability.dependsOn.beanObject.no_annotation.DependentBean;
import com.spring.ioc.bean.ability.dependsOn.beanObject.no_annotation.ResourceBean;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class ConfigurationDependsOn {

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
