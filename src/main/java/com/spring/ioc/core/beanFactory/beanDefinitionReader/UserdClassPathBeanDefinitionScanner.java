package com.spring.ioc.core.beanFactory.beanDefinitionReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.OrderComparator;
import org.springframework.core.PriorityOrdered;
import com.spring.ioc.javaConig.InstantiationContainerAnnotation;

public class UserdClassPathBeanDefinitionScanner {

	@Test
	public void instantiatContainer() {

		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		ClassPathBeanDefinitionScanner reader = new ClassPathBeanDefinitionScanner(beanFactory);
		reader.scan("com.spring.ioc.model.annotation");
		System.out.println("===================================================");
		System.out.println(Arrays.toString(beanFactory.getBeanDefinitionNames()));
		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
			System.out.println(beanDefinition);
		}
		System.out.println("===================================================");

	}

	@Test
	public void instantiatContainer2() {

		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		ClassPathBeanDefinitionScanner reader1 = new ClassPathBeanDefinitionScanner(beanFactory);
		AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
		reader.register(InstantiationContainerAnnotation.class);

		// 只加载InstantiatContainer
		System.out.println("===================================================");
		System.out.println(Arrays.toString(beanFactory.getBeanDefinitionNames()));
		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
			System.out.println(beanDefinition);
		}
		System.out.println("===================================================");

		// 注册ConfigurationClassPostProcessor用户加载Configuration JavaConfig中的类
		// 摘抄于 AbstractApplicationContext.invokeBeanFactoryPostProcessors
		Set<String> processedBeans = new HashSet<String>();
		String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true,
				false);

		List<BeanDefinitionRegistryPostProcessor> registryPostProcessors = new LinkedList<BeanDefinitionRegistryPostProcessor>();
		List<BeanDefinitionRegistryPostProcessor> priorityOrderedPostProcessors = new ArrayList<BeanDefinitionRegistryPostProcessor>();
		for (String ppName : postProcessorNames) {
			if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
				priorityOrderedPostProcessors
						.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
				processedBeans.add(ppName);
			}
		}
		sortPostProcessors(beanFactory, priorityOrderedPostProcessors);
		registryPostProcessors.addAll(priorityOrderedPostProcessors);
		invokeBeanDefinitionRegistryPostProcessors(priorityOrderedPostProcessors, beanFactory);

		// 只加载InstantiatContainer
		System.out.println("===================================================");
		System.out.println(Arrays.toString(beanFactory.getBeanDefinitionNames()));
		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
			System.out.println(beanDefinition);
		}
		System.out.println("===================================================");

	}

	private static void invokeBeanDefinitionRegistryPostProcessors(
			Collection<? extends BeanDefinitionRegistryPostProcessor> postProcessors, BeanDefinitionRegistry registry) {

		for (BeanDefinitionRegistryPostProcessor postProcessor : postProcessors) {
			postProcessor.postProcessBeanDefinitionRegistry(registry);
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
