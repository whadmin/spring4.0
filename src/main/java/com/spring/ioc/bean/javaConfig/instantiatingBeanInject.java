package com.spring.ioc.bean.javaConfig;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.spring.ioc.javaConig.InstantiatingBeanInjectAutowire;
import com.spring.ioc.model.HelloApiDataSource;
import com.spring.ioc.bean.getBean.populateBean.beanObject.no_annotation.ListBean;

public class instantiatingBeanInject {

	@Test
	public void instantiatingBeanInjectByName() {
		// 1.准备spring Java配置文件
		// 2.初始化容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				InstantiatingBeanInjectAutowire.class);
		// 3、从容器中获取Bean
		HelloApiDataSource helloApi = context.getBean("bean_byName", HelloApiDataSource.class);
		System.out.println(helloApi.getMessage());
		helloApi.getHelloApi().sayHello();

	}

	@Test
	public void instantiatingBeanInjectByType() {
		// 1.准备spring Java配置文件
		// 2.初始化容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				InstantiatingBeanInjectAutowire.class);
		// 3、从容器中获取Bean
		HelloApiDataSource helloApi = context.getBean("bean_byType", HelloApiDataSource.class);
		System.out.println(helloApi.getDataSource());
		System.out.println(helloApi.getMessage());
		helloApi.getHelloApi().sayHello();

	}

	@Test
	public void instantiatingBeanInjectByType2() {
		// 1.准备spring Java配置文件
		// 2.初始化容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				InstantiatingBeanInjectAutowire.class);
		// 3、从容器中获取Bean
		ListBean listBean = context.getBean("listBean", ListBean.class);
		// 对于集合接口根据类型注入将自动查找所有匹配的注入
		Assert.assertTrue(listBean.getList1().size() > 0);
		// 对于集合具体类型将根据具体类型进行注入，而不是选择所有泛型类型信息匹配的Bean
		Assert.assertTrue(listBean.getList2() == null);

	}
	
	@Test
	public void testAutowireByAutowired() {
		// 1.准备spring Java配置文件
		// 2.初始化容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				InstantiatingBeanInjectAutowire.class);
		HelloApiDataSource helloApi = context.getBean("bean_Autowired",
				HelloApiDataSource.class);
		System.out.println(helloApi.getDataSource());
		System.out.println(helloApi.getDataSource1());
		System.out.println(helloApi.getDataSource2());
		System.out.println(helloApi.getMessage());
		helloApi.getHelloApi().sayHello();

	}

}
