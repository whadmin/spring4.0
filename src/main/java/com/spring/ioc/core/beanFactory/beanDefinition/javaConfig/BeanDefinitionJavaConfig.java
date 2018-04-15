package com.spring.ioc.core.beanFactory.beanDefinition.javaConfig;

import java.util.Arrays;
import java.util.Map.Entry;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.config.ConstructorArgumentValues.ValueHolder;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.spring.ioc.javaConig.InstantiatingBean;
import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.ListBean;

public class BeanDefinitionJavaConfig {

	@Test
	public void readSouceCode() {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
		reader.register(InstantiatingBean.class);

		ConfigurationClassPostProcessor configurationClassPostProcessor = new ConfigurationClassPostProcessor();
		configurationClassPostProcessor.postProcessBeanDefinitionRegistry(beanFactory);
	}

	@Test
	public void testInstantiatingBeanByConstructor1() throws ClassNotFoundException {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
		reader.register(InstantiatingBean.class);

		ConfigurationClassPostProcessor configurationClassPostProcessor = new ConfigurationClassPostProcessor();
		configurationClassPostProcessor.postProcessBeanDefinitionRegistry(beanFactory);
		
		System.out.println(Arrays.toString(beanFactory.getBeanDefinitionNames()));
		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
			System.out.println(ReflectionToStringBuilder.toString(beanDefinition, ToStringStyle.MULTI_LINE_STYLE));
		}

		BeanDefinition bean1 = beanFactory.getBeanDefinition("bean1");
		System.out.println(ReflectionToStringBuilder.toString(bean1, ToStringStyle.MULTI_LINE_STYLE));
		HelloApi bean = beanFactory.getBean("bean1", HelloApi.class);
		bean.sayHello();


		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.HelloImpl3", null);
		beanDefinition1.setFactoryBeanName("instantiatingBean");
		beanDefinition1.setFactoryMethodName("bean1");
		beanDefinition1.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);

		/** 注入bean的描述 name名称为bean1_at 别名为null **/
		BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition1, "bean1_at", null),
				beanFactory);

		BeanDefinition bean1_at = beanFactory.getBeanDefinition("bean1_at");

		System.out.println(ReflectionToStringBuilder.toString(bean1_at, ToStringStyle.MULTI_LINE_STYLE));

		bean = beanFactory.getBean("bean1_at", HelloApi.class);
		bean.sayHello();
	}

	@Test
	public void testInstantiatingBeanByInject1() throws ClassNotFoundException {

		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
		reader.register(InstantiatingBean.class);

		ConfigurationClassPostProcessor configurationClassPostProcessor = new ConfigurationClassPostProcessor();
		configurationClassPostProcessor.postProcessBeanDefinitionRegistry(beanFactory);

		BeanDefinition bean2 = beanFactory.getBeanDefinition("bean_byName");
		for (Entry<Integer, ValueHolder> entry : bean2.getConstructorArgumentValues().getIndexedArgumentValues()
				.entrySet()) {
			System.out.println(ReflectionToStringBuilder.toString(entry.getKey(), ToStringStyle.MULTI_LINE_STYLE));
			System.out.println(ReflectionToStringBuilder.toString(entry.getValue(), ToStringStyle.MULTI_LINE_STYLE));
		}
		HelloApi bean = beanFactory.getBean("bean_byName", HelloApi.class);
		bean.sayHello();
		
		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.DependencyInjectByStaticFactory", null);

		beanDefinition.setFactoryBeanName("instantiatingBean");
		beanDefinition.setFactoryMethodName("bean_byName");
		beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);

		/** 注入bean的描述 name名称为bean2_byIndex_at 别名为null **/
		BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition, "bean_byName_at", null),
				beanFactory);

		BeanDefinition bean2_at = beanFactory.getBeanDefinition("bean_byName_at");

		for (Entry<Integer, ValueHolder> entry : bean2_at.getConstructorArgumentValues().getIndexedArgumentValues()
				.entrySet()) {
			System.out.println(ReflectionToStringBuilder.toString(entry.getKey(), ToStringStyle.MULTI_LINE_STYLE));
			System.out.println(ReflectionToStringBuilder.toString(entry.getValue(), ToStringStyle.MULTI_LINE_STYLE));
		}

		bean = beanFactory.getBean("bean_byName_at", HelloApi.class);
		bean.sayHello();
	}
	
	public void testInstantiatingBeanByInject2() throws ClassNotFoundException {

		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
		reader.register(InstantiatingBean.class);

		ConfigurationClassPostProcessor configurationClassPostProcessor = new ConfigurationClassPostProcessor();
		configurationClassPostProcessor.postProcessBeanDefinitionRegistry(beanFactory);

		BeanDefinition bean2 = beanFactory.getBeanDefinition("bean_byType");
		for (Entry<Integer, ValueHolder> entry : bean2.getConstructorArgumentValues().getIndexedArgumentValues()
				.entrySet()) {
			System.out.println(ReflectionToStringBuilder.toString(entry.getKey(), ToStringStyle.MULTI_LINE_STYLE));
			System.out.println(ReflectionToStringBuilder.toString(entry.getValue(), ToStringStyle.MULTI_LINE_STYLE));
		}
		HelloApi bean = beanFactory.getBean("bean_byType", HelloApi.class);
		bean.sayHello();
		
		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.DependencyInjectByStaticFactory", null);

		beanDefinition.setFactoryBeanName("instantiatingBean");
		beanDefinition.setFactoryMethodName("bean_byType");
		beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

		/** 注入bean的描述 name名称为bean2_byIndex_at 别名为null **/
		BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition, "bean_byType_at", null),
				beanFactory);

		BeanDefinition bean_byType_at = beanFactory.getBeanDefinition("bean_byType_at");

		for (Entry<Integer, ValueHolder> entry : bean_byType_at.getConstructorArgumentValues().getIndexedArgumentValues()
				.entrySet()) {
			System.out.println(ReflectionToStringBuilder.toString(entry.getKey(), ToStringStyle.MULTI_LINE_STYLE));
			System.out.println(ReflectionToStringBuilder.toString(entry.getValue(), ToStringStyle.MULTI_LINE_STYLE));
		}

		bean = beanFactory.getBean("bean_byType_at", HelloApi.class);
		bean.sayHello();
	}
	
	public void testInstantiatingBeanByConstructor2() throws ClassNotFoundException {

		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
		reader.register(InstantiatingBean.class);

		ConfigurationClassPostProcessor configurationClassPostProcessor = new ConfigurationClassPostProcessor();
		configurationClassPostProcessor.postProcessBeanDefinitionRegistry(beanFactory);

		BeanDefinition bean2 = beanFactory.getBeanDefinition("listBean");
		for (Entry<Integer, ValueHolder> entry : bean2.getConstructorArgumentValues().getIndexedArgumentValues()
				.entrySet()) {
			System.out.println(ReflectionToStringBuilder.toString(entry.getKey(), ToStringStyle.MULTI_LINE_STYLE));
			System.out.println(ReflectionToStringBuilder.toString(entry.getValue(), ToStringStyle.MULTI_LINE_STYLE));
		}
		HelloApi bean = beanFactory.getBean("listBean", HelloApi.class);
		bean.sayHello();
		
		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.DependencyInjectByStaticFactory", null);

		beanDefinition.setFactoryBeanName("instantiatingBean");
		beanDefinition.setFactoryMethodName("listBean");
		beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);

		/** 注入bean的描述 name名称为bean2_byIndex_at 别名为null **/
		BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition, "listBean_at", null),
				beanFactory);

		BeanDefinition listBean_at = beanFactory.getBeanDefinition("listBean_at");

		for (Entry<Integer, ValueHolder> entry : listBean_at.getConstructorArgumentValues().getIndexedArgumentValues()
				.entrySet()) {
			System.out.println(ReflectionToStringBuilder.toString(entry.getKey(), ToStringStyle.MULTI_LINE_STYLE));
			System.out.println(ReflectionToStringBuilder.toString(entry.getValue(), ToStringStyle.MULTI_LINE_STYLE));
		}

		ListBean listBean = beanFactory.getBean("listBean_at", ListBean.class);
	}
}
