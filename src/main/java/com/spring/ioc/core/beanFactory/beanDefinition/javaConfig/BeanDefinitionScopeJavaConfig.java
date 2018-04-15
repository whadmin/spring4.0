package com.spring.ioc.core.beanFactory.beanDefinition.javaConfig;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.spring.ioc.javaConig.InstantiatingBeanScope;
import com.spring.ioc.model.Printer;

public class BeanDefinitionScopeJavaConfig {

	@Test
	public void readSouceCode() {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
		reader.register(InstantiatingBeanScope.class);

		ConfigurationClassPostProcessor configurationClassPostProcessor = new ConfigurationClassPostProcessor();
		configurationClassPostProcessor.postProcessBeanDefinitionRegistry(beanFactory);
	}

	@Test
	public void testSingleton() throws Exception {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		Resource resource = new ClassPathResource("ioc/instantiatingBeanScope.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);

		Printer printer1 = beanFactory.getBean("singletonPrinter", Printer.class);
		Printer printer2 = beanFactory.getBean("singletonPrinter", Printer.class);
		System.out.println(printer1.equals(printer2));

		/**
		 * 构造如下配置的描述
		 * <bean id="singletonPrinter" class="com.spring.ioc.model.Printer" scope=
		 * "singleton" lazy-init="false"/>
		 **/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.Printer", null);
		beanDefinition1.setScope("singleton");
		beanDefinition1.setLazyInit(false);

		/** 注入bean的描述 name名称为bean1_at 别名为null **/
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition1, "singletonPrinter_at", null), beanFactory);

		Printer singletonPrinter_at1 = beanFactory.getBean("singletonPrinter_at", Printer.class);
		Printer singletonPrinter_at2 = beanFactory.getBean("singletonPrinter_at", Printer.class);
		System.out.println(singletonPrinter_at1.equals(singletonPrinter_at2));
	}

	@Test
	public void testPrototype() throws Exception {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		Resource resource = new ClassPathResource("ioc/instantiatingBeanScope.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);

		Printer printer1 = beanFactory.getBean("prototypePrinter", Printer.class);
		Printer printer2 = beanFactory.getBean("prototypePrinter", Printer.class);
		System.out.println(printer1.equals(printer2));

		/**
		 * 构造如下配置的描述
		 * <bean id="prototypePrinter" class="com.spring.ioc.model.Printer" scope=
		 * "prototype"/>
		 **/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.Printer", null);
		beanDefinition1.setScope("prototype");

		/** 注入bean的描述 name名称为bean1_at 别名为null **/
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition1, "prototypePrinter_at", null), beanFactory);

		Printer prototypePrinter_at1 = beanFactory.getBean("prototypePrinter_at", Printer.class);
		Printer prototypePrinter_at2 = beanFactory.getBean("prototypePrinter_at", Printer.class);
		System.out.println(prototypePrinter_at1.equals(prototypePrinter_at2));
	}
}
