package com.spring.ioc.core.beanFactory.beanDefinition.javaConfig;

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
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.spring.ioc.javaConig.InstantiatingBean;
import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.ListBean;

/**
 * API方式实现@Bean注入BeanDefinition
 */
public class BeanDefinitionJavaConfig {

	@Test
	public void readSouceCode() {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
		reader.register(InstantiatingBean.class);

		ConfigurationClassPostProcessor configurationClassPostProcessor = new ConfigurationClassPostProcessor();
		configurationClassPostProcessor.postProcessBeanDefinitionRegistry(beanFactory);
	}

	/**
	 * 手动构造JavaConfig @Bean注入的bean
	 */
	@Test
	public void testInstantiatingBeanByConstructor1() throws ClassNotFoundException {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
		reader.register(InstantiatingBean.class);
		
		//打印bean1beanFactory所有beanDefinition名称和详细，
		/**
		 * AnnotatedBeanDefinitionReader为我们加载了许多Spring自带的bean对象
		 * 1 解析@Configuration注解bean，识别@ComponentScan,@Bean加载进入容器
		 * org.springframework.context.annotation.internalConfigurationAnnotationProcessor ConfigurationClassPostProcessor
		 * 2 识别解析@Autowired
		 * org.springframework.context.annotation.internalAutowiredAnnotationProcessor
		 * org.springframework.context.annotation.internalRequiredAnnotationProcessor
		 * 3 识别解析@Resource
		 * org.springframework.context.annotation.internalCommonAnnotationProcessor
		 * instantiatingBean
         *
		 */
		System.out.println(Arrays.toString(beanFactory.getBeanDefinitionNames()));
		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
			System.out.println(ReflectionToStringBuilder.toString(beanDefinition, ToStringStyle.MULTI_LINE_STYLE));
		}
		
		//创建解析@Configuration注解bean，识别@ComponentScan,@Bean加载进入容器
		ConfigurationClassPostProcessor configurationClassPostProcessor = new ConfigurationClassPostProcessor();
		configurationClassPostProcessor.postProcessBeanDefinitionRegistry(beanFactory);

		//获取注解@Bean注入的bean1,打印beanDefinition结构并执行
		//通过结构可知JavaConfig @Bea注解方法的bean对象是通过工厂实例方法去构造,且是构造函数自动注入
		BeanDefinition bean1 = beanFactory.getBeanDefinition("bean1");
		System.out.println(ReflectionToStringBuilder.toString(bean1, ToStringStyle.MULTI_LINE_STYLE));
		HelloApi bean = beanFactory.getBean("bean1", HelloApi.class);
		bean.sayHello();

        
		//手动创建一个bean1的克隆注入并执行
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.HelloImpl3", null);
		beanDefinition1.setFactoryBeanName("instantiatingBean");
		beanDefinition1.setFactoryMethodName("bean1");
		beanDefinition1.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);

		//注入容器中
		BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition1, "bean1_at", null),
				beanFactory);

		BeanDefinition bean1_at = beanFactory.getBeanDefinition("bean1_at");
		System.out.println(ReflectionToStringBuilder.toString(bean1_at, ToStringStyle.MULTI_LINE_STYLE));

		//执行克隆bean对象
		bean = beanFactory.getBean("bean1_at", HelloApi.class);
		bean.sayHello();
	}
}
