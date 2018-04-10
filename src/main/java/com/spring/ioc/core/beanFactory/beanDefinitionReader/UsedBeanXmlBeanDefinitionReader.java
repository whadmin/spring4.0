package com.spring.ioc.core.beanFactory.beanDefinitionReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.annotation.TestServiceImpl;

import junit.framework.Assert;

public class UsedBeanXmlBeanDefinitionReader {

	@Test
	public void instantiatContainer() {
		// 1.准备配置文件，从文件系统获取配置文件，默认是相对路径，可以指定绝对路径
		Resource resource = new ClassPathResource("ioc/instantiatContainer.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);

		System.out.println(Arrays.toString(beanFactory.getBeanDefinitionNames()));
		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
			System.out.println(beanDefinition);
		}

	}

	@Test
	public void instantiationContainerAnnotation1() {
		Resource resource = new ClassPathResource("ioc/instantiationContainerAnnotation.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
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

		//注册AutowiredAnnotationBeanPostProcessor(AutowiredAnnotationBeanPostProcessor针对bean中@Autowired进行自动注入的功能)
		String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);
		List<BeanPostProcessor> priorityOrderedPostProcessors = new ArrayList<BeanPostProcessor>();
		List<BeanPostProcessor> internalPostProcessors = new ArrayList<BeanPostProcessor>();
		List<String> orderedPostProcessorNames = new ArrayList<String>();
		List<String> nonOrderedPostProcessorNames = new ArrayList<String>();
		for (String ppName : postProcessorNames) {
			if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
				BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
				priorityOrderedPostProcessors.add(pp);
				if (pp instanceof MergedBeanDefinitionPostProcessor) {
					internalPostProcessors.add(pp);
				}
			}
		}
		sortPostProcessors(beanFactory, priorityOrderedPostProcessors);
		registerBeanPostProcessors(beanFactory, priorityOrderedPostProcessors);

		//消除缓存
		beanFactory.destroySingleton("testService");
		service = beanFactory.getBean("testService", TestServiceImpl.class);
		Assert.assertNotNull(service.getDao());
	}

	public void instantiationContainerAnnotation2() {
		Resource resource = new ClassPathResource("ioc/instantiationContainerAnnotation.xml");
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
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

	private static void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory,
			List<BeanPostProcessor> postProcessors) {

		for (BeanPostProcessor postProcessor : postProcessors) {
			beanFactory.addBeanPostProcessor(postProcessor);
		}
	}

	private static void sortPostProcessors(ConfigurableListableBeanFactory beanFactory, List<?> postProcessors) {
		Comparator<Object> comparatorToUse = null;
		if (beanFactory instanceof DefaultListableBeanFactory) {
			comparatorToUse = ((DefaultListableBeanFactory) beanFactory).getDependencyComparator();
		}
		if (comparatorToUse == null) {
			comparatorToUse = OrderComparator.INSTANCE;
		}
		Collections.sort(postProcessors, comparatorToUse);
	}

}
