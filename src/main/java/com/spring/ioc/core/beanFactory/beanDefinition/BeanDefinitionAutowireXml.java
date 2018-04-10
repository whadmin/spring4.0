package com.spring.ioc.core.beanFactory.beanDefinition;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.spring.ioc.model.HelloApi;

public class BeanDefinitionAutowireXml {
	
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

		Resource resource = new ClassPathResource("ioc/instantiatingBeanInjectAutowire.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		/**		
		    <!-- 通过构造器注入 -->
		    <bean id="bean_byName" 
		          class="com.spring.ioc.model.HelloApiDecorator" 
		          autowire="byName"/>
		 **/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.HelloApiDecorator", null);
		beanDefinition1.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
		
		BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition1, "bean_byName_at", null),
				beanFactory);
	
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
		BeanDefinition bean_byName_at = beanFactory.getBeanDefinition("bean_byName_at");
		BeanDefinition bean_byName = beanFactory.getBeanDefinition("bean_byName");
		System.out.println(ReflectionToStringBuilder.toString(bean_byName_at, ToStringStyle.MULTI_LINE_STYLE));
		System.out.println(ReflectionToStringBuilder.toString(bean_byName, ToStringStyle.MULTI_LINE_STYLE));
		
		HelloApi bean = beanFactory.getBean("bean_byName_at", HelloApi.class);
		bean.sayHello();
	    bean = beanFactory.getBean("bean_byName", HelloApi.class);
		bean.sayHello();
	}
	
	@Test
	public void testInstantiatingBeanByType() throws ClassNotFoundException {

		Resource resource = new ClassPathResource("ioc/instantiatingBeanInjectAutowire.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		/**		
		   <bean id="bean_byType1" 
                  class="com.spring.ioc.model.HelloApiDecorator" 
                  autowire="byType"/>
          
		   <bean id="bean_byType2" 
		          class="com.spring.ioc.model.HelloApiDecorator" 
		          autowire="byType"/>
		          
		   <bean id="bean_byType3" 
		          class="com.spring.ioc.model.HelloApiDecorator" 
		          autowire="byType"/>
		 **/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.HelloApiDecorator", null);
		beanDefinition1.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
		
		AbstractBeanDefinition beanDefinition2 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.HelloApiDecorator", null);
		beanDefinition1.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
		
		AbstractBeanDefinition beanDefinition3 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.HelloApiDecorator", null);
		beanDefinition1.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
		
		BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition1, "bean_byType1_at", null),
				beanFactory);
		BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition2, "bean_byType2_at", null),
				beanFactory);
		BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition3, "bean_byType3_at", null),
				beanFactory);
	
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
		BeanDefinition bean_byType1_at = beanFactory.getBeanDefinition("bean_byType1_at");
		BeanDefinition bean_byType1 = beanFactory.getBeanDefinition("bean_byType1");
		System.out.println(ReflectionToStringBuilder.toString(bean_byType1_at, ToStringStyle.MULTI_LINE_STYLE));
		System.out.println(ReflectionToStringBuilder.toString(bean_byType1, ToStringStyle.MULTI_LINE_STYLE));
		
		HelloApi bean = beanFactory.getBean("bean_byType1_at", HelloApi.class);
		bean.sayHello();
	    bean = beanFactory.getBean("bean_byType1", HelloApi.class);
		bean.sayHello();
	}
	
	@Test
	public void testInstantiatingBeanByConstructor() throws ClassNotFoundException {

		Resource resource = new ClassPathResource("ioc/instantiatingBeanInjectAutowire.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		/**		
		    <!-- 通过构造器注入 -->
		    <bean id="bean_constructor" 
			          class="com.spring.ioc.model.HelloApiDecorator" 
			          autowire="constructor"/> 
		 **/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
		
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.HelloApiDecorator", null);
		beanDefinition1.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
		
		BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition1, "bean_constructor_at", null),
				beanFactory);
	
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
		BeanDefinition bean_constructor_at = beanFactory.getBeanDefinition("bean_constructor_at");
		BeanDefinition bean_constructor = beanFactory.getBeanDefinition("bean_constructor");
		System.out.println(ReflectionToStringBuilder.toString(bean_constructor_at, ToStringStyle.MULTI_LINE_STYLE));
		System.out.println(ReflectionToStringBuilder.toString(bean_constructor, ToStringStyle.MULTI_LINE_STYLE));
		
		HelloApi bean = beanFactory.getBean("bean_constructor_at", HelloApi.class);
		bean.sayHello();
	    bean = beanFactory.getBean("bean_constructor", HelloApi.class);
		bean.sayHello();
	}
	
	

}
