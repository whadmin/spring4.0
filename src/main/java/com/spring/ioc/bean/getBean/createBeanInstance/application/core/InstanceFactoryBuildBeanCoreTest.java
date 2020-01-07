package com.spring.ioc.bean.getBean.createBeanInstance.application.core;

import java.util.Map.Entry;

import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorHaveParamBean;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.config.ConstructorArgumentValues.ValueHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.spring.ioc.model.HelloApi;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *  xml实例工厂创建bean,API实现
 */
public class InstanceFactoryBuildBeanCoreTest {

	@Test
	public void readSouceCode() {
		Resource resource = new ClassPathResource("ioc/bean/getBean/createBeanInstance/instanceFactoryBuildBean.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
	}


	@Test
	public void testInstantiatingBeanByStaticFactory() throws ClassNotFoundException {
		// 1 定义BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2 定义beanDefinition
		/* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByInstanceFactory",
				null);

		AbstractBeanDefinition beanDefinition2 = BeanDefinitionReaderUtils.createBeanDefinition(null, null,
				null);

		// 定义普通属性
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
				object1);
		beanDefinition2.setFactoryBeanName("instanceFactory_at");
		beanDefinition2.setFactoryMethodName("newInstance");
		beanDefinition2.getConstructorArgumentValues().addIndexedArgumentValue(
				0, valueHolder1);

		// 3 注册到 BeanFactory
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition1, "instanceFactory_at", null),
				beanFactory);

		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition2, "instanceFactoryBuildBean_byIndex_at", null),
				beanFactory);

		// 4 获取注册BeanDefinition
		System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("instanceFactoryBuildBean_byIndex_at"),
				ToStringStyle.MULTI_LINE_STYLE));

		ConstructorHaveParamBean bean1 = beanFactory.getBean("instanceFactoryBuildBean_byIndex_at", ConstructorHaveParamBean.class);
		assertThat(bean1.getMessage()).isNotNull();
	}

	@Test
	public void testInstantiatingBeanByStaticFactory2() throws ClassNotFoundException {
		// 1 定义BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2 定义beanDefinition
		/* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByInstanceFactory",
				null);

		AbstractBeanDefinition beanDefinition2 = BeanDefinitionReaderUtils.createBeanDefinition(null, null,
				null);

		// 定义普通属性
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
				object1);
		valueHolder1.setType("java.lang.String");

		beanDefinition2.setFactoryBeanName("instanceFactory_at");
		beanDefinition2.setFactoryMethodName("newInstance");
		beanDefinition2.getConstructorArgumentValues().addGenericArgumentValue(
				valueHolder1);

		// 3 注册到 BeanFactory
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition1, "instanceFactory_at", null),
				beanFactory);

		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition2, "instanceFactoryBuildBean_byType_at", null),
				beanFactory);

		// 4 获取注册BeanDefinition
		System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("instanceFactoryBuildBean_byType_at"),
				ToStringStyle.MULTI_LINE_STYLE));

		ConstructorHaveParamBean bean1 = beanFactory.getBean("instanceFactoryBuildBean_byType_at", ConstructorHaveParamBean.class);
		assertThat(bean1.getMessage()).isNotNull();
	}

	@Test
	public void testInstantiatingBeanByStaticFactory3() throws ClassNotFoundException {
		// 1 定义BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2 定义beanDefinition
		/* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByInstanceFactory",
				null);

		AbstractBeanDefinition beanDefinition2 = BeanDefinitionReaderUtils.createBeanDefinition(null, null,
				null);

		// 定义普通属性
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
				object1);
		valueHolder1.setName("message");

		beanDefinition2.setFactoryBeanName("instanceFactory_at");
		beanDefinition2.setFactoryMethodName("newInstance");
		beanDefinition2.getConstructorArgumentValues().addGenericArgumentValue(
				valueHolder1);

		// 3 注册到 BeanFactory
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition1, "instanceFactory_at", null),
				beanFactory);

		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition2, "instanceFactoryBuildBean_byName_at", null),
				beanFactory);

		// 4 获取注册BeanDefinition
		System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("instanceFactoryBuildBean_byName_at"),
				ToStringStyle.MULTI_LINE_STYLE));

		ConstructorHaveParamBean bean1 = beanFactory.getBean("instanceFactoryBuildBean_byName_at", ConstructorHaveParamBean.class);
		assertThat(bean1.getMessage()).isNotNull();
	}

	@Test
	public void testInstantiatingBeanByStaticFactory4() throws ClassNotFoundException {
		// 1 定义BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2 定义beanDefinition
		/* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByInstanceFactory",
				null);

		AbstractBeanDefinition beanDefinition2 = BeanDefinitionReaderUtils.createBeanDefinition(null, null,
				null);

		AbstractBeanDefinition beanDefinition3 = BeanDefinitionReaderUtils.createBeanDefinition(null, "java.lang.String",
				null);

		// 定义普通属性
		RuntimeBeanReference object1 = new RuntimeBeanReference("message");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
				object1);

		beanDefinition2.setFactoryBeanName("instanceFactory_at");
		beanDefinition2.setFactoryMethodName("newInstance");
		beanDefinition2.getConstructorArgumentValues().addGenericArgumentValue(
				valueHolder1);

		TypedStringValue object2 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder2 = new ConstructorArgumentValues.ValueHolder(
				object2);
		beanDefinition3.getConstructorArgumentValues().addIndexedArgumentValue(
				0, valueHolder2);

		// 3 注册到 BeanFactory
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition1, "instanceFactory_at", null),
				beanFactory);

		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition2, "instanceFactoryBuildBean_byRef_at", null),
				beanFactory);

		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition3, "message", null),
				beanFactory);

		// 4 获取注册BeanDefinition
		System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("instanceFactoryBuildBean_byRef_at"),
				ToStringStyle.MULTI_LINE_STYLE));

		ConstructorHaveParamBean bean1 = beanFactory.getBean("instanceFactoryBuildBean_byRef_at", ConstructorHaveParamBean.class);
		assertThat(bean1.getMessage()).isNotNull();
	}
}
