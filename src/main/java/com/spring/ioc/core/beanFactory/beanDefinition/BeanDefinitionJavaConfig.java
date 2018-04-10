package com.spring.ioc.core.beanFactory.beanDefinition;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

import com.spring.ioc.javaConig.InstantiatContainer;

public class BeanDefinitionJavaConfig {

	@Test
	public void BeanDefinitionForJavaConfig() {

		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
		reader.register(InstantiatContainer.class);

		BeanDefinition beanDefinition = beanFactory.getBeanDefinition("hello");
		System.out.println(beanDefinition.toString());
	}

}
