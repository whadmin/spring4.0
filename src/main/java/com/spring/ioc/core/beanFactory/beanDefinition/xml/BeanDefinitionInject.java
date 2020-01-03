package com.spring.ioc.core.beanFactory.beanDefinition.xml;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.spring.ioc.model.HelloApi;

/**
 *  xml property 主动注入bean,API实现
 */
public class BeanDefinitionInject {
	
	@Test
	public void testInstantiatingBeanByInject() throws ClassNotFoundException {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		
		Resource resource = new ClassPathResource("ioc/bean/getBean/injection/beanNoAutowireBean.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
		
		BeanDefinition bean5 = beanFactory.getBeanDefinition("bean5");
		System.out.println(ReflectionToStringBuilder.toString(bean5, ToStringStyle.MULTI_LINE_STYLE));
		
		HelloApi bean = beanFactory.getBean("bean5", HelloApi.class);
		bean.sayHello();

		/**
			<!-- 通过setter方式进行依赖注入 -->
			<bean id="bean5" class="com.spring.ioc.model.HelloImpl4">
				<property name="message" value="Hello World!" />
				<property name="index">
					<value>1</value>
				</property>
			</bean>
		 **/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.HelloImpl4", null);
		
		TypedStringValue object1 = new TypedStringValue("Hello World!");
		object1.setSource(null);
		PropertyValue pv1 = new PropertyValue("message", object1);
		pv1.setSource(null);
		
		TypedStringValue object2 = new TypedStringValue("1");
		object1.setSource(null);
		PropertyValue pv2 = new PropertyValue("index", object2);
		pv2.setSource(null);
		
		/** 注入bean的描述  name名称为bean1_at 别名为null **/
		BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition1, "bean5_at", null),
				beanFactory);
		beanDefinition1.getPropertyValues().addPropertyValue(pv1);
		beanDefinition1.getPropertyValues().addPropertyValue(pv2);

		BeanDefinition bean5_at = beanFactory.getBeanDefinition("bean5_at");
		System.out.println(ReflectionToStringBuilder.toString(bean5_at, ToStringStyle.MULTI_LINE_STYLE));

		bean = beanFactory.getBean("bean5_at", HelloApi.class);
		bean.sayHello();
	}
	
	@Test
	public void testInstantiatingBeanByInject2() throws ClassNotFoundException {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		Resource resource = new ClassPathResource("ioc/bean/getBean/injection/beanNoAutowireBean.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
		
		BeanDefinition bean5_ref = beanFactory.getBeanDefinition("bean5_ref");
		System.out.println(ReflectionToStringBuilder.toString(bean5_ref, ToStringStyle.MULTI_LINE_STYLE));
		
		HelloApi bean = beanFactory.getBean("bean5_ref", HelloApi.class);
		bean.sayHello();

		/**
			<bean id="bean5_ref" class="com.spring.ioc.model.HelloImpl4">
				<property name="message" ref="message" />
				<property name="index" ref="index"/>
			</bean>
		 **/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.HelloImpl4", null);
		
		/** 定义普通属性,并设置属性类型**/
		RuntimeBeanReference object1 = new RuntimeBeanReference("message");
		object1.setSource(null);
		PropertyValue pv1 = new PropertyValue("message", object1);
		pv1.setSource(null);
		
		/** 定义普通属性,并设置属性类型**/
		RuntimeBeanReference object2 = new RuntimeBeanReference("index");
		object2.setSource(null);
		PropertyValue pv2 = new PropertyValue("index", object2);
		pv2.setSource(null);
				
		/** 注入bean的描述  name名称为bean1_at 别名为null **/
		BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition1, "bean5_ref_at", null),
				beanFactory);
		beanDefinition1.getPropertyValues().addPropertyValue(pv1);
		beanDefinition1.getPropertyValues().addPropertyValue(pv2);

		BeanDefinition bean5_ref_at = beanFactory.getBeanDefinition("bean5_ref_at");
		System.out.println(ReflectionToStringBuilder.toString(bean5_ref_at, ToStringStyle.MULTI_LINE_STYLE));
			
		bean = beanFactory.getBean("bean5_ref_at", HelloApi.class);
		bean.sayHello();
	}

}
