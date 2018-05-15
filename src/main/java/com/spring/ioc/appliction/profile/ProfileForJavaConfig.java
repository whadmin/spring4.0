package com.spring.ioc.appliction.profile;

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
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.OrderComparator;
import org.springframework.core.PriorityOrdered;

import com.spring.ioc.appliction.profile.model.HelloService;
import com.spring.ioc.appliction.profile.model.HelloService2;
import com.spring.ioc.javaConig.profile.ProfileConfig;
import com.spring.ioc.javaConig.profile.annotation.ProfileConfigAnnotation;

public class ProfileForJavaConfig {

	@Test
	public void testProfileConfig() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		context.register(ProfileConfig.class);
		context.refresh();
		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
		System.out.println(context.getEnvironment());

		HelloService2 helloService2 = context.getBean(HelloService2.class);
		System.out.println(helloService2.sayHello());

		context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles("dev");
		context.register(ProfileConfig.class);
		context.refresh();

		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
		HelloService helloService = context.getBean(HelloService.class);
		System.out.println(helloService.sayHello());

		context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles("produce");
		context.register(ProfileConfig.class);
		context.refresh();

		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
		helloService = context.getBean(HelloService.class);
		System.out.println(helloService.sayHello());

		context.close();

	}

	@Test
	public void testDynamicProfileConfig() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		context.getEnvironment().setActiveProfiles("dev"); // ①
		context.register(ProfileConfigAnnotation.class);// ②
		context.refresh(); // ③

		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));

		HelloService helloService = context.getBean(HelloService.class);
		System.out.println(helloService.sayHello());

		context.close();

	}

	@Test
	public void testProfileConfigAnnotation() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		context.register(ProfileConfigAnnotation.class);
		context.refresh();
		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
		System.out.println(context.getEnvironment());

		HelloService2 helloService2 = context.getBean(HelloService2.class);
		System.out.println(helloService2.sayHello());

		context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles("dev");
		context.register(ProfileConfigAnnotation.class);
		context.refresh();

		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
		HelloService helloService = context.getBean(HelloService.class);
		System.out.println(helloService.sayHello());

		context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles("produce");
		context.register(ProfileConfigAnnotation.class);
		context.refresh();

		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
		helloService = context.getBean(HelloService.class);
		System.out.println(helloService.sayHello());

		context.close();

	}

	public void dynamicJavaConfig(DefaultListableBeanFactory beanFactory) {
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
