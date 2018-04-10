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

public class BeanDefinitionStaticFactoryXml {

	@Test
	public void testInstantiatingBeanByStaticFactory() throws ClassNotFoundException {
		Resource resource = new ClassPathResource("ioc/instantiatingBean.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
		
		/**
		 * 	<!-- 通过静态工厂参数索引方式依赖注入 -->
			<bean id="bean3_byIndex"
				class="com.spring.ioc.model.DependencyInjectByStaticFactory"
				factory-method="newInstance">
				<constructor-arg index="0" value="Hello World!" />
				<constructor-arg index="1" value="1" />
			</bean>
         ***/

		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.DependencyInjectByStaticFactory", null);
		
		/** 定义普通属性**/
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(object1);
		
		/** 定义普通属性**/
		TypedStringValue object2 = new TypedStringValue("1");
		object2.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder2 = new ConstructorArgumentValues.ValueHolder(object2);
		
		/** 设置构造bean的参数按构造函数索引 **/
		beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, valueHolder1);
		beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(1, valueHolder2);
		beanDefinition.setFactoryMethodName("newInstance");
		
		/** 注入bean的描述  name名称为bean2_byIndex_at 别名为null **/
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition, "bean3_byIndex_at", null), beanFactory);

		BeanDefinition bean3_byIndex_at = beanFactory.getBeanDefinition("bean3_byIndex_at");
		BeanDefinition bean3_byIndex = beanFactory.getBeanDefinition("bean3_byIndex");
		for(Entry<Integer, ValueHolder> entry:bean3_byIndex_at.getConstructorArgumentValues().getIndexedArgumentValues().entrySet()) {
			System.out.println(ReflectionToStringBuilder.toString(entry.getKey(), ToStringStyle.MULTI_LINE_STYLE));
			System.out.println(ReflectionToStringBuilder.toString(entry.getValue(), ToStringStyle.MULTI_LINE_STYLE));
		}
		for(Entry<Integer, ValueHolder> entry:bean3_byIndex.getConstructorArgumentValues().getIndexedArgumentValues().entrySet()) {
			System.out.println(ReflectionToStringBuilder.toString(entry.getKey(), ToStringStyle.MULTI_LINE_STYLE));
			System.out.println(ReflectionToStringBuilder.toString(entry.getValue(), ToStringStyle.MULTI_LINE_STYLE));
		}
		
		HelloApi byIndex = beanFactory.getBean("bean3_byIndex_at", HelloApi.class);
		byIndex.sayHello();
		byIndex = beanFactory.getBean("bean3_byIndex", HelloApi.class);
		byIndex.sayHello();
	}
	
	@Test
	public void testInstantiatingBeanByStaticFactory2() throws ClassNotFoundException {
		Resource resource = new ClassPathResource("ioc/instantiatingBean.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
				
		/**
		 * 
		<!-- 通过静态工厂参数类型方式依赖注入 -->
		<bean id="bean3_byType"
			class="com.spring.ioc.model.DependencyInjectByStaticFactory"
			factory-method="newInstance">
			<constructor-arg type="java.lang.String"
				value="Hello World!" />
			<constructor-arg type="int" value="2" />
		</bean>
        ***/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.DependencyInjectByStaticFactory", null);
		
		/** 定义普通属性,并设置属性类型**/
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(object1);
		valueHolder1.setType("java.lang.String");
		
		/** 定义普通属性,并设置属性类型**/
		TypedStringValue object2 = new TypedStringValue("1");
		object2.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder2 = new ConstructorArgumentValues.ValueHolder(object2);
		valueHolder2.setType("int");
		
		/** 设置构造bean的参数按属性类型 **/
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(valueHolder1);
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(valueHolder2);
		beanDefinition.setFactoryMethodName("newInstance");
		
		/** 注册bean的描述信息 **/
		BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition, "bean3_byType_at", null),
				beanFactory);

		BeanDefinition bean3_byType_at = beanFactory.getBeanDefinition("bean3_byType_at");
		BeanDefinition bean3_byType = beanFactory.getBeanDefinition("bean3_byType");
		
		for(ConstructorArgumentValues.ValueHolder valueHolder:bean3_byType_at.getConstructorArgumentValues().getGenericArgumentValues()) {
			System.out.println(ReflectionToStringBuilder.toString(valueHolder, ToStringStyle.MULTI_LINE_STYLE));
		}
		for(ConstructorArgumentValues.ValueHolder valueHolder:bean3_byType.getConstructorArgumentValues().getGenericArgumentValues()) {
			System.out.println(ReflectionToStringBuilder.toString(valueHolder, ToStringStyle.MULTI_LINE_STYLE));
		}
		
		HelloApi byIndex = beanFactory.getBean("bean3_byType_at", HelloApi.class);
		byIndex.sayHello();
		byIndex = beanFactory.getBean("bean3_byType", HelloApi.class);
		byIndex.sayHello();
	}
	
	@Test
	public void testInstantiatingBeanByStaticFactory3() throws ClassNotFoundException {

		Resource resource = new ClassPathResource("ioc/instantiatingBean.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
				
		/**
		 * 
			<!-- 通过静态工厂参数名称方式依赖注入 -->
			<bean id="bean3_byName"
				class="com.spring.ioc.model.DependencyInjectByStaticFactory"
				factory-method="newInstance">
				<constructor-arg name="message" value="Hello World!" />
				<constructor-arg name="index" value="3" />
			</bean>
        ***/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.DependencyInjectByStaticFactory", null);
		
		/** 定义普通属性,并设置属性类型**/
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder1 = new ConstructorArgumentValues.ValueHolder(object1);
		valueHolder1.setName("message");;
		
		/** 定义普通属性,并设置属性类型**/
		TypedStringValue object2 = new TypedStringValue("1");
		object2.setSource(null);
		ConstructorArgumentValues.ValueHolder valueHolder2 = new ConstructorArgumentValues.ValueHolder(object2);
		valueHolder2.setName("index");
		
		/** 设置构造bean的参数按属性名称 **/
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(valueHolder1);
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(valueHolder2);
		beanDefinition.setFactoryMethodName("newInstance");
		
		/** 注册bean的描述信息 **/
		BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition, "bean3_byName_at", null),
				beanFactory);

		BeanDefinition bean3_byName_at = beanFactory.getBeanDefinition("bean3_byName_at");
		BeanDefinition bean3_byName = beanFactory.getBeanDefinition("bean3_byName");
		
		for(ConstructorArgumentValues.ValueHolder valueHolder:bean3_byName_at.getConstructorArgumentValues().getGenericArgumentValues()) {
			System.out.println(ReflectionToStringBuilder.toString(valueHolder, ToStringStyle.MULTI_LINE_STYLE));
		}
		for(ConstructorArgumentValues.ValueHolder valueHolder:bean3_byName.getConstructorArgumentValues().getGenericArgumentValues()) {
			System.out.println(ReflectionToStringBuilder.toString(valueHolder, ToStringStyle.MULTI_LINE_STYLE));
		}
		
		HelloApi byIndex = beanFactory.getBean("bean3_byName_at", HelloApi.class);
		byIndex.sayHello();
		byIndex = beanFactory.getBean("bean3_byName", HelloApi.class);
		byIndex.sayHello();
	}

}
