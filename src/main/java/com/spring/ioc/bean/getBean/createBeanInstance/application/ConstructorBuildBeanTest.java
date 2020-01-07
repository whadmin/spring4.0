package com.spring.ioc.bean.getBean.createBeanInstance.application;

import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.annotation.AutowireByConstructorQualifierBean;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.AutowireByConstructorMultipleMatches;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.AutowireByConstructor;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorNoParamBean;
import com.spring.ioc.bean.getBean.createBeanInstance.beanObject.no_annotation.ConstructorHaveParamBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 使用构造函数实例化bean
 */
public class ConstructorBuildBeanTest {

	/**
	 * 使用默认构造函数实例化bean
	 */
	@Test
	public void testConstructorNoParamBean() {
		// 使用构造器
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/bean/getBean/createBeanInstance/constructorBuildBean.xml");
		ConstructorNoParamBean bean = context.getBean("constructorDefaultBean", ConstructorNoParamBean.class);
		assertThat(bean.getMessage()).isNotNull();
	}

	/**
	 * 使用有参数构造函数实例化bean
	 */
	@Test
	public void testConstructorHaveParamBean() {
		// 使用构造器
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/bean/getBean/createBeanInstance/constructorBuildBean.xml");
        // 获取根据参数索引依赖注入的Bean
		ConstructorHaveParamBean byIndex = context.getBean("constructorParamBean_byIndex", ConstructorHaveParamBean.class);
		assertThat(byIndex.getMessage()).isNotNull();

		// 获取根据参数类型依赖注入的Bean
		ConstructorHaveParamBean byType = context.getBean("constructorParamBean_byType", ConstructorHaveParamBean.class);
		assertThat(byType.getMessage()).isNotNull();

		// 获取根据参数名字依赖注入的Bean
		ConstructorHaveParamBean byName = context.getBean("constructorParamBean_byName", ConstructorHaveParamBean.class);
		assertThat(byName.getMessage()).isNotNull();

		ConstructorHaveParamBean bean2_byRef = context.getBean("constructorParamBean_byRef",
				ConstructorHaveParamBean.class);
		assertThat(bean2_byRef.getMessage()).isNotNull();
	}

	/**
	 * 使用有参数构造函数实例化bean
	 *
	 * 同时通过ByConstructor 自动注入 默认按照名称匹配的方式注入
	 */
	@Test
	public void testAutowireByConstructor() {
		// 使用构造器
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/bean/getBean/createBeanInstance/autowireByConstructor.xml");
		// 获取根据参数索引依赖注入的Bean
		AutowireByConstructor bean_true = context.getBean("autowire_Byconstructor", AutowireByConstructor.class);
		assertThat(bean_true.getMysqlDataSource()).isNotNull();
		assertThat(bean_true.getOracleDataSource()).isNotNull();
	}

	/**
	 * 使用有参数构造函数实例化bean
	 *
	 * 同时通过 ByConstructor 自动注入 如果名称无法匹配，则按照类型注入，依赖存在多个相同类型时报错
	 */
	@Test(expected = Exception.class)
	public void testAutowireByConstructorError() {
		// 使用构造器
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/bean/getBean/createBeanInstance/autowireByConstructorError.xml");
	}

	/**
	 * 使用有参数构造函数实例化bean
	 *
	 * 同时通过  ByConstructor 如果名称无法匹配，则按照类型注入，依赖存在多个相同类型时通过Primary 设置优先匹配注入规则
	 */
	@Test
	public void testAutowireByConstructorPrimary() {
		// 使用构造器a
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/bean/getBean/createBeanInstance/autowireByConstructorPrimary.xml");
		// 获取根据参数索引依赖注入的Bean
		AutowireByConstructorMultipleMatches bean_error = context.getBean("autowire_Byconstructor_primary", AutowireByConstructorMultipleMatches.class);
		assertThat(bean_error.getDataSource()).isNotNull();
	}


	@Test(expected = Exception.class)
	public void testAutowireByConstructorQualifierFalse() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/bean/getBean/createBeanInstance/autowireByConstructorQualifierFalse.xml");
		AutowireByConstructorQualifierBean bean1 = context.getBean("autowire_Byconstructor_qualifier",
				AutowireByConstructorQualifierBean.class);
		assertThat(bean1.getMysqlDataSource()).isNotNull();
		assertThat(bean1.getOracleDataSource()).isNotNull();
	}

	@Test
	public void testAutowireByConstructorQualifierTrue() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"ioc/bean/getBean/createBeanInstance/autowireByConstructorQualifierTrue.xml");
		AutowireByConstructorQualifierBean bean1 = context.getBean("autowire_Byconstructor_qualifier",
				AutowireByConstructorQualifierBean.class);
		assertThat(bean1.getMysqlDataSource()).isNotNull();
		assertThat(bean1.getOracleDataSource()).isNotNull();
	}





//	/**
//	 * 构造函数自动注入
//	 * @throws ClassNotFoundException
//	 */
//	@Test
//	public void testInstantiatingBeanByConstructor2() throws ClassNotFoundException {
//		// 2.初始化容器
//		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//
//		Resource resource = new ClassPathResource("ioc/bean/getBean/createBeanInstance/autowireByConstructorQualifierFalse.xml");
//		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
//		reader.loadBeanDefinitions(resource);
//
//		BeanDefinition bean_constructor = beanFactory.getBeanDefinition("bean_constructor");
//		System.out.println(ReflectionToStringBuilder.toString(bean_constructor, ToStringStyle.MULTI_LINE_STYLE));
//
//		HelloApiDataSource helloApi = beanFactory.getBean("bean_constructor", HelloApiDataSource.class);
//		helloApi.getHelloApi().sayHello();
//		/**
//		 * <!-- 通过构造器注入 -->
//		 * <bean id="bean_constructor" class="com.spring.ioc.model.HelloApiDecorator"
//		 * autowire="createBeanInstance"/>
//		 **/
//		/** 摘抄BeanDefinitionParserDelegate.parseBeanDefinitionElement **/
//
//		AbstractBeanDefinition beanDefinition1 = BeanDefinitionReaderUtils.createBeanDefinition(null,
//				"com.spring.ioc.model.HelloApiDecorator", null);
//		beanDefinition1.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
//
//		BeanDefinitionReaderUtils.registerBeanDefinition(
//				new BeanDefinitionHolder(beanDefinition1, "bean_constructor_at", null), beanFactory);
//
//
//		BeanDefinition bean_constructor_at = beanFactory.getBeanDefinition("bean_constructor_at");
//		System.out.println(ReflectionToStringBuilder.toString(bean_constructor_at, ToStringStyle.MULTI_LINE_STYLE));
//
//
//		helloApi = beanFactory.getBean("bean_constructor_at", HelloApiDataSource.class);
//		helloApi.getHelloApi().sayHello();
//	}
}
