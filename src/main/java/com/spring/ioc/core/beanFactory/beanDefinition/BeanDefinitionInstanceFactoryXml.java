package com.spring.ioc.core.beanFactory.beanDefinition;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.spring.ioc.model.HelloApi;

public class BeanDefinitionInstanceFactoryXml {

	@Test
	public void testInstantiatingBeanByStaticFactory() throws ClassNotFoundException {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		Resource resource = new ClassPathResource("ioc/instantiatingBean.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);

		BeanDefinition bean4_byIndex = beanFactory.getBeanDefinition("bean4_byIndex");
		for (Entry<Integer, ValueHolder> entry : bean4_byIndex.getConstructorArgumentValues().getIndexedArgumentValues()
				.entrySet()) {
			System.out.println(ReflectionToStringBuilder.toString(entry.getKey(), ToStringStyle.MULTI_LINE_STYLE));
			System.out.println(ReflectionToStringBuilder.toString(entry.getValue(), ToStringStyle.MULTI_LINE_STYLE));
		}

		HelloApi byIndex = beanFactory.getBean("bean4_byIndex", HelloApi.class);
		byIndex.sayHello();

		/**
		 * <bean id="bean4_byIndex" factory-bean="instanceFactory" factory-method=
		 * "newInstance"> <constructor-arg index="0" value="Hello World!" />
		 * <constructor-arg index="1" value="1" /> </bean>
		 ***/

		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.DependencyInjectByInstanceFactory", null);

		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition1, "instanceFactory", null), beanFactory);

		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, null, null);

		/** 定义普通属性 **/
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(object1);

		/** 定义普通属性 **/
		TypedStringValue object2 = new TypedStringValue("1");
		object2.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder2 = new ConstructorArgumentValues.ValueHolder(object2);

		/** 设置构造bean的参数按构造函数索引 **/
		beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, valueHolder1);
		beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(1, valueHolder2);
		beanDefinition.setFactoryBeanName("instanceFactory");
		beanDefinition.setFactoryMethodName("newInstance");

		/** 注入bean的描述 name名称为bean2_byIndex_at 别名为null **/
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition, "bean4_byIndex_at", null), beanFactory);

		BeanDefinition bean4_byIndex_at = beanFactory.getBeanDefinition("bean4_byIndex_at");

		for (Entry<Integer, ValueHolder> entry : bean4_byIndex_at.getConstructorArgumentValues()
				.getIndexedArgumentValues().entrySet()) {
			System.out.println(ReflectionToStringBuilder.toString(entry.getKey(), ToStringStyle.MULTI_LINE_STYLE));
			System.out.println(ReflectionToStringBuilder.toString(entry.getValue(), ToStringStyle.MULTI_LINE_STYLE));
		}

		byIndex = beanFactory.getBean("bean4_byIndex_at", HelloApi.class);
		byIndex.sayHello();
	}

	@Test
	public void testInstantiatingBeanByStaticFactory2() throws ClassNotFoundException {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		Resource resource = new ClassPathResource("ioc/instantiatingBean.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);

		BeanDefinition bean4_byType = beanFactory.getBeanDefinition("bean4_byType");
		for (ConstructorArgumentValues.ValueHolder valueHolder : bean4_byType.getConstructorArgumentValues()
				.getGenericArgumentValues()) {
			System.out.println(ReflectionToStringBuilder.toString(valueHolder, ToStringStyle.MULTI_LINE_STYLE));
		}
		HelloApi byType = beanFactory.getBean("bean4_byType", HelloApi.class);
		byType.sayHello();
		/**
		 * 
		 * <bean id="bean4_byType" factory-bean="instanceFactory" factory-method=
		 * "newInstance">
		 * <constructor-arg type="java.lang.String" value="Hello World!" />
		 * <constructor-arg type="int" value="2" /> </bean> </bean>
		 ***/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.DependencyInjectByInstanceFactory", null);

		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition1, "instanceFactory", null), beanFactory);

		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.DependencyInjectByStaticFactory", null);

		/** 定义普通属性,并设置属性类型 **/
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(object1);
		valueHolder1.setType("java.lang.String");

		/** 定义普通属性,并设置属性类型 **/
		TypedStringValue object2 = new TypedStringValue("2");
		object2.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder2 = new ConstructorArgumentValues.ValueHolder(object2);
		valueHolder2.setType("int");

		/** 设置构造bean的参数按属性类型 **/
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(valueHolder1);
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(valueHolder2);
		beanDefinition.setFactoryBeanName("instanceFactory");
		beanDefinition.setFactoryMethodName("newInstance");

		/** 注册bean的描述信息 **/
		BeanDefinitionReaderUtils
				.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition, "bean4_byType_at", null), beanFactory);

		BeanDefinition bean4_byType_at = beanFactory.getBeanDefinition("bean4_byType_at");

		for (ConstructorArgumentValues.ValueHolder valueHolder : bean4_byType_at.getConstructorArgumentValues()
				.getGenericArgumentValues()) {
			System.out.println(ReflectionToStringBuilder.toString(valueHolder, ToStringStyle.MULTI_LINE_STYLE));
		}

		byType = beanFactory.getBean("bean4_byType_at", HelloApi.class);
		byType.sayHello();
	}

	@Test
	public void testInstantiatingBeanByStaticFactory3() throws ClassNotFoundException {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		Resource resource = new ClassPathResource("ioc/instantiatingBean.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);

		BeanDefinition bean4_byType = beanFactory.getBeanDefinition("bean4_byName");
		for (ConstructorArgumentValues.ValueHolder valueHolder : bean4_byType.getConstructorArgumentValues()
				.getGenericArgumentValues()) {
			System.out.println(ReflectionToStringBuilder.toString(valueHolder, ToStringStyle.MULTI_LINE_STYLE));
		}
		HelloApi byType = beanFactory.getBean("bean4_byName", HelloApi.class);
		byType.sayHello();

		/**
		 * 
		 * <bean id="bean4_byName" factory-bean="instanceFactory" factory-method=
		 * "newInstance"> <constructor-arg name="message" value="Hello World!" />
		 * <constructor-arg name="index" value="3" /> </bean>
		 ***/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.DependencyInjectByInstanceFactory", null);

		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition1, "instanceFactory", null), beanFactory);

		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.DependencyInjectByStaticFactory", null);

		/** 定义普通属性,并设置属性类型 **/
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(object1);
		valueHolder1.setName("message");

		/** 定义普通属性,并设置属性类型 **/
		TypedStringValue object2 = new TypedStringValue("3");
		object2.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder2 = new ConstructorArgumentValues.ValueHolder(object2);
		valueHolder2.setName("index");

		/** 设置构造bean的参数按属性名称 **/
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(valueHolder1);
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(valueHolder2);
		beanDefinition.setFactoryBeanName("instanceFactory");
		beanDefinition.setFactoryMethodName("newInstance");

		/** 注册bean的描述信息 **/
		BeanDefinitionReaderUtils
				.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition, "bean4_byName_at", null), beanFactory);

		BeanDefinition bean4_byName_at = beanFactory.getBeanDefinition("bean4_byName_at");

		for (ConstructorArgumentValues.ValueHolder valueHolder : bean4_byName_at.getConstructorArgumentValues()
				.getGenericArgumentValues()) {
			System.out.println(ReflectionToStringBuilder.toString(valueHolder, ToStringStyle.MULTI_LINE_STYLE));
		}

		HelloApi byName = beanFactory.getBean("bean4_byName_at", HelloApi.class);
		byName.sayHello();
	}
}
