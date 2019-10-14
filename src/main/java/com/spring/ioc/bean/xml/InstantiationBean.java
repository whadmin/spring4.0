package com.spring.ioc.bean.xml;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.model.ArrayTestBean;
import com.spring.ioc.model.BooleanTestBean;
import com.spring.ioc.model.CollectionTestBean;
import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.IdRefTestBean;
import com.spring.ioc.model.ListTestBean;
import com.spring.ioc.model.MapTestBean;
import com.spring.ioc.model.PropertiesTestBean;
import com.spring.ioc.model.SetTestBean;

import junit.framework.Assert;


/**
 * 实例化bean的方式有三种
 *
 * 1 使用构造函数实例化bean
 *
 * 2 使用静态工厂
 *
 * 3 实例工厂
 *
 */
public class InstantiationBean {

	/**
	 * 使用构造函数实例化bean
	 */
	@Test
	public void testInstantiatingBeanByConstructor() {
		// 使用构造器
		BeanFactory beanFactory = new ClassPathXmlApplicationContext(
				"ioc/instantiatingBean.xml");
		HelloApi bean1 = beanFactory.getBean("bean1", HelloApi.class);
		bean1.sayHello();

		// 获取根据参数索引依赖注入的Bean
		HelloApi byIndex = beanFactory.getBean("bean2_byIndex", HelloApi.class);
		byIndex.sayHello();

		// 获取根据参数类型依赖注入的Bean
		HelloApi byType = beanFactory.getBean("bean2_byType", HelloApi.class);
		byType.sayHello();

		// 获取根据参数名字依赖注入的Bean
		HelloApi byName = beanFactory.getBean("bean2_byName", HelloApi.class);
		byName.sayHello();

		HelloApi bean2_byRef = beanFactory.getBean("bean2_byRef",
				HelloApi.class);
		bean2_byRef.sayHello();
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
}
