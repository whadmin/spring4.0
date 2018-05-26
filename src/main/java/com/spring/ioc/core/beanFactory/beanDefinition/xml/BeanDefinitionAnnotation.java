package com.spring.ioc.core.beanFactory.beanDefinition.xml;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.spring.ioc.model.annotation.TestServiceImpl;


/**
 *  xml component-scan ,API实现
 */
public class BeanDefinitionAnnotation {

	@Test
	public void testBeanDefinitionAnnotation() throws IOException {
	
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		Resource resource = new ClassPathResource("ioc/instantiationBeanAnnotation.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);

		//当xml中配置<context:component-scan> 会加载spring内置的BeanDefinition
		System.out.println("===================================================");
		System.out.println(Arrays.toString(beanFactory.getBeanDefinitionNames()));
		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
			System.out.println(beanDefinition);
		}
		System.out.println("===================================================");
		
		TestServiceImpl service = beanFactory.getBean("testService", TestServiceImpl.class);
		Assert.assertNull(service.getDao());

		AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
		autowiredAnnotationBeanPostProcessor.setBeanFactory(beanFactory);
		beanFactory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);
		
		//消除缓存
		beanFactory.destroySingleton("testService");
		service = beanFactory.getBean("testService", TestServiceImpl.class);
		Assert.assertNotNull(service.getDao());
	}

}
