package com.spring.ioc.bean.getBean.constructor;

import com.spring.ioc.bean.getBean.constructor.beanObject.no_annotation.ConstructorDefaultBean;
import com.spring.ioc.bean.getBean.constructor.beanObject.no_annotation.ConstructorParamBean;
import com.spring.ioc.model.HelloApiDataSource;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.model.HelloApi;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 */
public class ConstructorBean {

	/**
	 * 使用默认构造函数实例化bean
	 */
	@Test
	public void testConstructorDefaultBean() {
		// 使用构造器
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/instantiatingBean.xml");
		ConstructorDefaultBean bean = context.getBean("constructorDefaultBean", ConstructorDefaultBean.class);
		assertThat(bean.getMessage()).isNotNull();
	}

	/**
	 * 使用有参数构造函数实例化bean
	 */
	@Test
	public void testConstructorParamBean() {
		// 使用构造器
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/instantiatingBean.xml");
        // 获取根据参数索引依赖注入的Bean
		ConstructorParamBean byIndex = context.getBean("constructorParamBean_byIndex", ConstructorParamBean.class);
		assertThat(byIndex.getMessage()).isNotNull();

		// 获取根据参数类型依赖注入的Bean
		ConstructorParamBean byType = context.getBean("constructorParamBean_byType", ConstructorParamBean.class);
		assertThat(byType.getMessage()).isNotNull();

		// 获取根据参数名字依赖注入的Bean
		ConstructorParamBean byName = context.getBean("constructorParamBean_byName", ConstructorParamBean.class);
		assertThat(byName.getMessage()).isNotNull();

		ConstructorParamBean bean2_byRef = context.getBean("constructorParamBean_byRef",
				ConstructorParamBean.class);
		assertThat(bean2_byRef.getMessage()).isNotNull();
	}

	/**
	 * 使用静态工厂实例化bean
	 */
	@Test
	public void testInstantiatingBeanByStaticFactory() {
		// 使用静态工厂方法
		BeanFactory beanFactory = new ClassPathXmlApplicationContext(
				"ioc/instantiatingBean.xml");
		// 获取根据参数索引依赖注入的Bean
		HelloApi byIndex = beanFactory.getBean("bean3_byIndex", HelloApi.class);
		byIndex.sayHello();

		// 获取根据参数类型依赖注入的Bean
		HelloApi byType = beanFactory.getBean("bean3_byType", HelloApi.class);
		byType.sayHello();

		// 获取根据参数名字依赖注入的Bean
		HelloApi byName = beanFactory.getBean("bean3_byName", HelloApi.class);
		byName.sayHello();
	}

	/**
	 * 使用实例工厂实例化bean
	 */
	@Test
	public void testInstantiatingBeanByInstanceFactory() {
		// 使用实例工厂方法
		BeanFactory beanFactory = new ClassPathXmlApplicationContext(
				"ioc/instantiatingBean.xml");
		// 获取根据参数索引依赖注入的Bean
		HelloApi byIndex = beanFactory.getBean("bean4_byIndex", HelloApi.class);
		byIndex.sayHello();

		// 获取根据参数类型依赖注入的Bean
		HelloApi byType = beanFactory.getBean("bean4_byType", HelloApi.class);
		byType.sayHello();

		// 获取根据参数名字依赖注入的Bean
		HelloApi byName = beanFactory.getBean("bean4_byName", HelloApi.class);
		byName.sayHello();

	}

	/**
	 * 构造函数自动注入
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testInstantiatingBeanByConstructor2() throws ClassNotFoundException {
		// 2.初始化容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		Resource resource = new ClassPathResource("ioc/bean/getBean/methodInjection/beanAutowireByAnnotationQualifier.xml");
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);

		BeanDefinition bean_constructor = beanFactory.getBeanDefinition("bean_constructor");
		System.out.println(ReflectionToStringBuilder.toString(bean_constructor, ToStringStyle.MULTI_LINE_STYLE));

		HelloApiDataSource helloApi = beanFactory.getBean("bean_constructor", HelloApiDataSource.class);
		helloApi.getHelloApi().sayHello();
		/**
		 * <!-- 通过构造器注入 -->
		 * <bean id="bean_constructor" class="com.spring.ioc.model.HelloApiDecorator"
		 * autowire="constructor"/>
		 **/
		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/

		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
				"com.spring.ioc.model.HelloApiDecorator", null);
		beanDefinition1.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);

		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(beanDefinition1, "bean_constructor_at", null), beanFactory);


		BeanDefinition bean_constructor_at = beanFactory.getBeanDefinition("bean_constructor_at");
		System.out.println(ReflectionToStringBuilder.toString(bean_constructor_at, ToStringStyle.MULTI_LINE_STYLE));


		helloApi = beanFactory.getBean("bean_constructor_at", HelloApiDataSource.class);
		helloApi.getHelloApi().sayHello();
	}
}
