package com.spring.ioc.bean.getBean.constructor;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.spring.ioc.model.HelloApi;

/**
 *  xml构造方法创建bean,API实现
 */
public class BeanDefinitionInstantiatingConstructor {

	@Test
	public void readSouceCode() {
		Resource resource = new ClassPathResource("ioc/instantiatingBean.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
	}

	@Test
	public void testInstantiatingBeanByConstructor() throws ClassNotFoundException {		
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		Resource resource = new ClassPathResource("ioc/instantiatingBean.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
		
		
		System.out.println(Arrays.toString(beanFactory.getBeanDefinitionNames()));
		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
			System.out.println(ReflectionToStringBuilder.toString(beanDefinition, ToStringStyle.MULTI_LINE_STYLE));
		}

		//获取<bean name="bean1"... 的bean,打印beanDefinition结构并执行
		/*
		  	<!--使用默认构造参数 -->
	        <bean name="bean1" class="com.spring.ioc.model.HelloImpl" destroy-method="destroy" init-method="init" />
		 */
		BeanDefinition bean1 = beanFactory.getBeanDefinition("bean1");
		System.out.println(ReflectionToStringBuilder.toString(bean1, ToStringStyle.MULTI_LINE_STYLE));
		HelloApi bean = beanFactory.getBean("bean1", HelloApi.class);
		bean.sayHello();
		
		//手动创建一个bean1的克隆注入并执行
		/* 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null, "com.spring.ioc.model.HelloImpl",
						null);
		// 设置bean的初始化方法
		beanDefinition1.setInitMethodName("init");
		// 设置bean的初始化方法开启
		beanDefinition1.setEnforceInitMethod(false);
		// 设置bean的销毁方法
		beanDefinition1.setDestroyMethodName("destroy");
		// 设置bean的销毁方法开启
		beanDefinition1.setEnforceDestroyMethod(false);
		// 设置beanDefinition1 构造来源
		beanDefinition1.setResource(null);

        /* 注入bean的描述 name名称为bean1_at 别名为null **/
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition1, "bean1_at", null),
				beanFactory);

		BeanDefinition bean1_at = beanFactory.getBeanDefinition("bean1_at");

		System.out.println(ReflectionToStringBuilder.toString(bean1_at,
				ToStringStyle.MULTI_LINE_STYLE));

		bean = beanFactory.getBean("bean1_at", HelloApi.class);
		bean.sayHello();
	}

	@Test
	public void testInstantiatingBeanByConstructor1()
			throws ClassNotFoundException {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		Resource resource = new ClassPathResource("ioc/instantiatingBean.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(
				beanFactory);
		reader.loadBeanDefinitions(resource);

		//获取<bean name="bean2_byIndex"... 的bean,打印beanDefinition结构并执行
		/**
		    <!-- constructorDependencyInject star -->
			<!-- 通过构造器参数索引方式依赖注入 -->
			<bean id="bean2_byIndex" class="com.spring.ioc.model.HelloImpl3">
				<constructor-arg index="0" value="Hello World!" />
				<constructor-arg index="1" value="1" />
			</bean>
		 */
		BeanDefinition bean2_byIndex = beanFactory.getBeanDefinition("bean2_byIndex");

		System.out.println(ReflectionToStringBuilder.toString(bean2_byIndex, ToStringStyle.MULTI_LINE_STYLE));
//		for (Entry<Integer, ValueHolder> entry : bean2_byIndex.getConstructorArgumentValues().getIndexedArgumentValues().entrySet()) {
//			System.out.println(ReflectionToStringBuilder.toString(
//					entry.getKey(), ToStringStyle.MULTI_LINE_STYLE));
//			System.out.println(ReflectionToStringBuilder.toString(
//					entry.getValue(), ToStringStyle.MULTI_LINE_STYLE));
//		}
		HelloApi byIndex = beanFactory.getBean("bean2_byIndex", HelloApi.class);
		byIndex.sayHello();


		//手动创建一个bean2_byIndex的克隆注入并执行
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils
				.createBeanDefinition(null, "com.spring.ioc.model.HelloImpl3",
						null);

		/** 定义普通属性 **/
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
				object1);

		/** 定义普通属性 **/
		TypedStringValue object2 = new TypedStringValue("1");
		object2.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder2 = new ConstructorArgumentValues.ValueHolder(
				object2);

		/** 设置构造bean的参数按构造函数索引 **/
		beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(
				0, valueHolder1);
		beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(
				1, valueHolder2);

		/** 注入bean的描述 name名称为bean2_byIndex_at 别名为null **/
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition, "bean2_byIndex_at",
						null), beanFactory);

		BeanDefinition bean2_byIndex_at = beanFactory
				.getBeanDefinition("bean2_byIndex_at");

		for (Entry<Integer, ValueHolder> entry : bean2_byIndex_at
				.getConstructorArgumentValues().getIndexedArgumentValues()
				.entrySet()) {
			System.out.println(ReflectionToStringBuilder.toString(
					entry.getKey(), ToStringStyle.MULTI_LINE_STYLE));
			System.out.println(ReflectionToStringBuilder.toString(
					entry.getValue(), ToStringStyle.MULTI_LINE_STYLE));
		}
		byIndex = beanFactory.getBean("bean2_byIndex_at", HelloApi.class);
		byIndex.sayHello();
	}

	@Test
	public void testInstantiatingBeanByConstructor2()
			throws ClassNotFoundException {

		Resource resource = new ClassPathResource("ioc/instantiatingBean.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(
				beanFactory);
		reader.loadBeanDefinitions(resource);

		BeanDefinition bean2_byType = beanFactory
				.getBeanDefinition("bean2_byType");
		for (ConstructorArgumentValues.ValueHolder valueHolder : bean2_byType
				.getConstructorArgumentValues().getGenericArgumentValues()) {
			System.out.println(ReflectionToStringBuilder.toString(valueHolder,
					ToStringStyle.MULTI_LINE_STYLE));
		}

		HelloApi byIndex = beanFactory.getBean("bean2_byType", HelloApi.class);
		byIndex.sayHello();

		/**
		 * 构造如下配置的描述 <!-- 通过构造器参数索引方式依赖注入 -->
		 * 
		 * <!-- 通过构造器参数类型方式依赖注入 --> <bean id="bean2_byType"
		 * class="com.spring.ioc.model.HelloImpl3"> <constructor-arg
		 * type="java.lang.String" value="Hello World!" /> <constructor-arg
		 * type="int" value="2" /> </bean>
		 ***/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils
				.createBeanDefinition(null, "com.spring.ioc.model.HelloImpl3",
						null);

		/** 定义普通属性,并设置属性类型 **/
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
				object1);
		valueHolder1.setType("java.lang.String");

		/** 定义普通属性,并设置属性类型 **/
		TypedStringValue object2 = new TypedStringValue("1");
		object2.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder2 = new ConstructorArgumentValues.ValueHolder(
				object2);
		valueHolder2.setType("int");

		/** 设置构造bean的参数按属性类型 **/
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(
				valueHolder1);
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(
				valueHolder2);

		/** 注册bean的描述信息 **/
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition, "bean2_byType_at",
						null), beanFactory);

		BeanDefinition bean2_byType_at = beanFactory
				.getBeanDefinition("bean2_byType_at");

		for (ConstructorArgumentValues.ValueHolder valueHolder : bean2_byType_at
				.getConstructorArgumentValues().getGenericArgumentValues()) {
			System.out.println(ReflectionToStringBuilder.toString(valueHolder,
					ToStringStyle.MULTI_LINE_STYLE));
		}

		byIndex = beanFactory.getBean("bean2_byType_at", HelloApi.class);
		byIndex.sayHello();
	}

	@Test
	public void testInstantiatingBeanByConstructor3() throws ClassNotFoundException {

		Resource resource = new ClassPathResource("ioc/instantiatingBean.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
		
		BeanDefinition bean2_byName = beanFactory.getBeanDefinition("bean2_byName");
		
		for (ConstructorArgumentValues.ValueHolder valueHolder : bean2_byName.getConstructorArgumentValues()
				.getGenericArgumentValues()) {
			System.out.println(ReflectionToStringBuilder.toString(valueHolder, ToStringStyle.MULTI_LINE_STYLE));
		}
		
		HelloApi byIndex = beanFactory.getBean("bean2_byName", HelloApi.class);
		byIndex.sayHello();

		/**
		 * 构造如下配置的描述 <!-- 通过构造器参数索引方式依赖注入 -->
		 * 
		 * <!-- 通过构造器参数名称方式依赖注入 -->
			  <bean id="bean2_byName" class="com.spring.ioc.model.HelloImpl3">
			    <constructor-arg name="message" value="Hello World!" />
		        <constructor-arg name="index" value="3" />
			  </bean>
		 ***/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils
				.createBeanDefinition(null, "com.spring.ioc.model.HelloImpl3",
						null);

		/** 定义普通属性,并设置属性类型 **/
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
				object1);
		valueHolder1.setName("message");
		;

		/** 定义普通属性,并设置属性类型 **/
		TypedStringValue object2 = new TypedStringValue("1");
		object2.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder2 = new ConstructorArgumentValues.ValueHolder(
				object2);
		valueHolder2.setName("index");

		/** 设置构造bean的参数按属性名称 **/
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(
				valueHolder1);
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(
				valueHolder2);

		/** 注册bean的描述信息 **/
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition, "bean2_byName_at",
						null), beanFactory);

		BeanDefinition bean2_byName_at = beanFactory
				.getBeanDefinition("bean2_byName_at");

		for (ConstructorArgumentValues.ValueHolder valueHolder : bean2_byName_at
				.getConstructorArgumentValues().getGenericArgumentValues()) {
			System.out.println(ReflectionToStringBuilder.toString(valueHolder,
					ToStringStyle.MULTI_LINE_STYLE));
		}

		byIndex = beanFactory.getBean("bean2_byName_at", HelloApi.class);
		byIndex.sayHello();
	}

	@Test
	public void testInstantiatingBeanByConstructor4() throws ClassNotFoundException {
		Resource resource = new ClassPathResource("ioc/instantiatingBean.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);

		BeanDefinition bean2_byRef = beanFactory.getBeanDefinition("bean2_byRef");
		for (ConstructorArgumentValues.ValueHolder valueHolder : bean2_byRef.getConstructorArgumentValues()
				.getGenericArgumentValues()) {
			System.out.println(ReflectionToStringBuilder.toString(valueHolder, ToStringStyle.MULTI_LINE_STYLE));
		}

		HelloApi bean = beanFactory.getBean("bean2_byRef", HelloApi.class);
		bean.sayHello();

		/**
		 * <!-- 通过构造器参数名称方式依赖注入 --> <bean id="message" class="java.lang.String">
		 * <constructor-arg index="0" value="Hello World!" /> </bean>
		 * <bean id="index" class="java.lang.String">
		 * <constructor-arg index="0" value="4" /> </bean>
		 *
		 * <bean id="bean2_byRef" class="com.spring.ioc.model.HelloImpl3">
		 * <constructor-arg name="message" ref="message" />
		 * <constructor-arg name="index" ref="index" /> </bean>
		 ***/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils
				.createBeanDefinition(null, "com.spring.ioc.model.HelloImpl3",
						null);

		/** 定义普通属性,并设置属性类型 **/
		RuntimeBeanReference object1 = new RuntimeBeanReference("message");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(
				object1);
		valueHolder1.setName("message");
		;

		/** 定义普通属性,并设置属性类型 **/
		RuntimeBeanReference object2 = new RuntimeBeanReference("index");
		object2.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder2 = new ConstructorArgumentValues.ValueHolder(
				object2);
		valueHolder2.setName("index");

		/** 设置构造bean的参数按属性名称 **/
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(
				valueHolder1);
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(
				valueHolder2);

		/** 注册bean的描述信息 **/
		BeanDefinitionReaderUtils
				.registerBeanDefinition(new BeanDefinitionHolder(
						beanDefinition, "bean2_byRef_at", null), beanFactory);

		BeanDefinition bean2_byRef_at = beanFactory
				.getBeanDefinition("bean2_byRef_at");

		for (ConstructorArgumentValues.ValueHolder valueHolder : bean2_byRef_at
				.getConstructorArgumentValues().getGenericArgumentValues()) {
			System.out.println(ReflectionToStringBuilder.toString(valueHolder,
					ToStringStyle.MULTI_LINE_STYLE));
		}

		bean = beanFactory.getBean("bean2_byRef_at", HelloApi.class);
		bean.sayHello();
	}
}
