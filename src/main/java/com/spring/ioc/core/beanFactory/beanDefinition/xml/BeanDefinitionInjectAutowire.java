package com.spring.ioc.core.beanFactory.beanDefinition.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.spring.ioc.core.beanFactory.beanDefinition.PostProcessorRegistrationDelegate;
import com.spring.ioc.model.HelloApiDataSource;

public class BeanDefinitionInjectAutowire{

	@Test
	public void readSouceCode() {
		Resource resource = new ClassPathResource("ioc/instantiatingBeanInjectAutowire.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
	}

	@Test
	public void testInstantiatingBeanByName() throws ClassNotFoundException {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		Resource resource = new ClassPathResource("ioc/instantiatingBeanInjectAutowire.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
		
		BeanDefinition bean_byName = beanFactory.getBeanDefinition("bean_byName");
		System.out.println(ReflectionToStringBuilder.toString(bean_byName, ToStringStyle.MULTI_LINE_STYLE));
		
		HelloApiDataSource helloApi = beanFactory.getBean("bean_byName", HelloApiDataSource.class);
		System.out.println(helloApi.getMessage());
		helloApi.getHelloApi().sayHello();
		
		/**
		 * 
		 * <!-- 通过构造器注入 -->
		 * <bean id="bean_byName" class="com.spring.ioc.model.HelloApiDecorator"  autowire="byName"/>
		 **/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/

		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.HelloApiDecorator", null);
		beanDefinition1.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
		beanDefinition1.setAutowireCandidate(true);

		BeanDefinitionReaderUtils
				.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition1, "bean_byName_at", null), beanFactory);
		BeanDefinition bean_byName_at = beanFactory.getBeanDefinition("bean_byName_at");
		System.out.println(ReflectionToStringBuilder.toString(bean_byName_at, ToStringStyle.MULTI_LINE_STYLE));
		
		helloApi = beanFactory.getBean("bean_byName_at", HelloApiDataSource.class);
		System.out.println(helloApi.getMessage());
		helloApi.getHelloApi().sayHello();
	}

	@Test
	public  void testInstantiatingBeanByType() throws ClassNotFoundException {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		
		Resource resource = new ClassPathResource("ioc/instantiatingBeanInjectAutowire.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
		

		//读取xml中装配的CustomAutowireConfigurer类型为beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false)
		//描述执行invokeBeanFactoryPostProcessors
		PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory,new ArrayList<BeanFactoryPostProcessor>());
		//beanFactory.setAutowireCandidateResolver(new QualifierAnnotationAutowireCandidateResolver());
		
		BeanDefinition bean_byType = beanFactory.getBeanDefinition("bean_byType");
		System.out.println(ReflectionToStringBuilder.toString(bean_byType, ToStringStyle.MULTI_LINE_STYLE));
		
		HelloApiDataSource helloApi = beanFactory.getBean("bean_byType", HelloApiDataSource.class);
		System.out.println(helloApi.getDataSource());
		System.out.println(helloApi.getDataSource1());
		System.out.println(helloApi.getDataSource2());
		helloApi.getHelloApi().sayHello();

		/**
		 * <bean id="bean_byType1" class="com.spring.ioc.model.HelloApiDecorator"
		 * autowire="byType"/>
		 * 
		 **/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/

		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.HelloApiDecorator", null);
		beanDefinition1.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition1, "bean_byType_at", null), beanFactory);

		BeanDefinition bean_byType_at = beanFactory.getBeanDefinition("bean_byType_at");
		System.out.println(ReflectionToStringBuilder.toString(bean_byType_at, ToStringStyle.MULTI_LINE_STYLE));
		
		helloApi = beanFactory.getBean("bean_byType_at", HelloApiDataSource.class);
		System.out.println(helloApi.getDataSource());
		System.out.println(helloApi.getDataSource1());
		System.out.println(helloApi.getDataSource2());
		helloApi.getHelloApi().sayHello();
	}
	
	@Test
	public void testInstantiatingBeanByAutowired() throws ClassNotFoundException {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		
		Resource resource = new ClassPathResource("ioc/instantiatingBeanInjectAutowire.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
		
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.HelloApiDecorator2", null);
		beanDefinition1.setAutowireCandidate(true);

		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition1, "bean_Autowired_at", null), beanFactory);
		
		//读取xml中装配的CustomAutowireConfigurer类型为beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false)的bean描述
		//用来支持@Qualifier
		//执行invokeBeanFactoryPostProcessors
		PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory,new ArrayList<BeanFactoryPostProcessor>());
		//读取xml中装配的AutowiredAnnotationBeanPostProcessor类型为beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false)的bean描述
		//用来主动注入@Autowired
		//读取xml中装配的CommonAnnotationBeanPostProcessor类型为beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false)的bean描述 
		//用来主动注入@Resource
		//添加到registerBeanPostProcessors ioc BeanPostProcessors列表中
		PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory,null);
		//beanFactory.setAutowireCandidateResolver(new QualifierAnnotationAutowireCandidateResolver());
		
		beanFactory.setAutowireCandidateResolver(new QualifierAnnotationAutowireCandidateResolver());
		
		BeanDefinition bean_byType = beanFactory.getBeanDefinition("bean_Autowired");
		System.out.println(ReflectionToStringBuilder.toString(bean_byType, ToStringStyle.MULTI_LINE_STYLE));
		
		HelloApiDataSource helloApi = beanFactory.getBean("bean_Autowired", HelloApiDataSource.class);
		System.out.println(helloApi.getDataSource());
		System.out.println(helloApi.getDataSource1());
		System.out.println(helloApi.getDataSource2());
		helloApi.getHelloApi().sayHello();

		/**
		 * <bean id="bean_byType1" class="com.spring.ioc.model.HelloApiDecorator"
		 * autowire="byType"/>
		 * 
		 **/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/



		BeanDefinition bean_Autowired_at = beanFactory.getBeanDefinition("bean_Autowired_at");
		System.out.println(ReflectionToStringBuilder.toString(bean_Autowired_at, ToStringStyle.MULTI_LINE_STYLE));
		
		helloApi = beanFactory.getBean("bean_Autowired_at", HelloApiDataSource.class);
		System.out.println(helloApi.getDataSource());
		System.out.println(helloApi.getDataSource1());
		System.out.println(helloApi.getDataSource2());
		helloApi.getHelloApi().sayHello();
	}

	@Test
	public void testInstantiatingBeanByConstructor() throws ClassNotFoundException {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		Resource resource = new ClassPathResource("ioc/instantiatingBeanInjectAutowire.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
		
		BeanDefinition bean_constructor = beanFactory.getBeanDefinition("bean_constructor");
		System.out.println(ReflectionToStringBuilder.toString(bean_constructor, ToStringStyle.MULTI_LINE_STYLE));
		
		HelloApiDataSource helloApi = beanFactory.getBean("bean_constructor", HelloApiDataSource.class);
		helloApi.getHelloApi().sayHello();
		/**
		 * <!-- 通过构造器注入 -->
		 * <bean id="bean_constructor" class="com.spring.ioc.model.HelloApiDecorator"
		 * autowire="constructor"/>
		 **/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/

		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.HelloApiDecorator", null);
		beanDefinition1.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);

		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition1, "bean_constructor_at", null), beanFactory);

		
		BeanDefinition bean_constructor_at = beanFactory.getBeanDefinition("bean_constructor_at");
		System.out.println(ReflectionToStringBuilder.toString(bean_constructor_at, ToStringStyle.MULTI_LINE_STYLE));
		

		helloApi = beanFactory.getBean("bean_constructor_at", HelloApiDataSource.class);
		helloApi.getHelloApi().sayHello();
	}
	
	

}
