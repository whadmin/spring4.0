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
 *  xml静态工厂创建bean,API实现
 */
public class StaticFactoryBuildBeanCoreTest {


	@Test
	public void readSouceCode() {
		Resource resource = new ClassPathResource("ioc/bean/getBean/createBeanInstance/staticFactoryBuildBean.xml");
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
		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByStaticFactory",
				null);

		// 定义普通属性
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
				object1);

		// 设置构造bean的参数按构造函数索引
		beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(
				0, valueHolder1);
		beanDefinition.setFactoryMethodName("newInstance");

		// 3 注册到 BeanFactory
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition, "staticFactoryBuildBean_byIndex_at", null),
				beanFactory);
		// 4 获取注册BeanDefinition
		System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("staticFactoryBuildBean_byIndex_at"),
				ToStringStyle.MULTI_LINE_STYLE));

		ConstructorHaveParamBean bean1 = beanFactory.getBean("staticFactoryBuildBean_byIndex_at", ConstructorHaveParamBean.class);
		assertThat(bean1.getMessage()).isNotNull();
	}
	
	@Test
	public void testInstantiatingBeanByStaticFactory2() throws ClassNotFoundException {
		// 1 定义BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2 定义beanDefinition
		/* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByStaticFactory",
				null);

		// 定义普通属性
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(object1);
		valueHolder1.setType("java.lang.String");

		// 设置构造bean的参数按构造函数索引
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(valueHolder1);
		beanDefinition.setFactoryMethodName("newInstance");

		// 3 注册到 BeanFactory
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition, "staticFactoryBuildBean_byType_at", null),
				beanFactory);
		// 4 获取注册BeanDefinition
		System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("staticFactoryBuildBean_byType_at"),
				ToStringStyle.MULTI_LINE_STYLE));

		ConstructorHaveParamBean bean1 = beanFactory.getBean("staticFactoryBuildBean_byType_at", ConstructorHaveParamBean.class);
		assertThat(bean1.getMessage()).isNotNull();
	}
	
	@Test
	public void testInstantiatingBeanByStaticFactory3() throws ClassNotFoundException {
		// 1 定义BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2 定义beanDefinition
		/* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByStaticFactory",
				null);

		// 定义普通属性
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(object1);
		valueHolder1.setName("message");;

		// 设置构造bean的参数按构造函数索引
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(valueHolder1);
		beanDefinition.setFactoryMethodName("newInstance");

		// 3 注册到 BeanFactory
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition, "staticFactoryBuildBean_byName_at", null),
				beanFactory);
		// 4 获取注册BeanDefinition
		System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("staticFactoryBuildBean_byName_at"),
				ToStringStyle.MULTI_LINE_STYLE));

		ConstructorHaveParamBean bean1 = beanFactory.getBean("staticFactoryBuildBean_byName_at", ConstructorHaveParamBean.class);
		assertThat(bean1.getMessage()).isNotNull();
	}


	@Test
	public void testInstantiatingBeanByStaticFactory4() throws ClassNotFoundException {
		// 1 定义BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2 定义beanDefinition
		/* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.bean.getBean.createBeanInstance.beanObject.factory.DependencyInjectByStaticFactory",
				null);

		AbstractBeanDefinition beanDefinition2 = BeanDefinitionReaderUtils.createBeanDefinition(null, "java.lang.String",
				null);

		// 定义普通属性
		RuntimeBeanReference object1 = new RuntimeBeanReference("message");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
				object1);

		// 设置构造bean的参数按构造函数索引
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(valueHolder1);
		beanDefinition.setFactoryMethodName("newInstance");

		TypedStringValue object2 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder2 = new ConstructorArgumentValues.ValueHolder(
				object2);
		beanDefinition2.getConstructorArgumentValues().addIndexedArgumentValue(
				0, valueHolder2);

		// 3 注册到 BeanFactory
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition, "staticFactoryBuildBean_byRef_at", null),
				beanFactory);

		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition2, "message", null),
				beanFactory);

		// 4 获取注册BeanDefinition
		System.err.println(ReflectionToStringBuilder.toString(beanFactory.getBeanDefinition("staticFactoryBuildBean_byRef_at"),
				ToStringStyle.MULTI_LINE_STYLE));

		ConstructorHaveParamBean bean1 = beanFactory.getBean("staticFactoryBuildBean_byRef_at", ConstructorHaveParamBean.class);
		assertThat(bean1.getMessage()).isNotNull();
	}

}
