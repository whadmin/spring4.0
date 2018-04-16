package com.spring.ioc.bean.javaConfig;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.ioc.javaConig.InstantiatingBean;
import com.spring.ioc.javaConig.InstantiatingBeanInjectAutowire;
import com.spring.ioc.model.ArrayTestBean;
import com.spring.ioc.model.BooleanTestBean;
import com.spring.ioc.model.CollectionTestBean;
import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.IdRefTestBean;
import com.spring.ioc.model.ListBean;
import com.spring.ioc.model.ListTestBean;
import com.spring.ioc.model.MapTestBean;
import com.spring.ioc.model.PropertiesTestBean;
import com.spring.ioc.model.SetTestBean;

public class instantiatingBeanInjectJavaConfig {

	@Test
	public void instantiatingBeanInjectByName() {
		// 1.准备spring Java配置文件
		// 2.初始化容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				InstantiatingBeanInjectAutowire.class);
		// 3、从容器中获取Bean
		HelloApi bean1 = context.getBean("bean1", HelloApi.class);
		bean1.sayHello();
	}

	@Test
	public void instantiatingBeanInjectByType1() {
		// 1.准备spring Java配置文件
		// 2.初始化容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				InstantiatingBeanInjectAutowire.class);
		// 3、从容器中获取Bean
		HelloApi bean1 = context.getBean("bean2", HelloApi.class);
		bean1.sayHello();
	}

	@Test
	public void instantiatingBeanInjectByType2() {
		// 1.准备spring Java配置文件
		// 2.初始化容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				InstantiatingBeanInjectAutowire.class);
		// 3、从容器中获取Bean
		HelloApi bean1 = context.getBean("bean1", HelloApi.class);
		bean1.sayHello();
	}

}
