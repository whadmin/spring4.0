package com.spring.ioc.bean.assemblyBean.testRuning.conditional;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



import com.spring.ioc.bean.assemblyBean.beanObject.condition.ConditionalService;
import com.spring.ioc.bean.assemblyBean.beanObject.condition.ListService;
import com.spring.ioc.bean.assemblyBean.javaConfig.condition.CustomConditionConifg;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomConditionalTest {


	/**
	 * 不启动环境配置
	 */
	@Test
	public void testCustomConditional() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CustomConditionConifg.class);
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CustomConditionConifg.class);
		assertTrue(context.containsBeanDefinition("onBeanConditionalService"));
		assertTrue(context.containsBeanDefinition("conditionalOnBean"));
//
//		ConditionalService conditionalService1 = (ConditionalService) context.getBean("onBeanConditionalService");
//		System.out.println(conditionalService1);
//
//		ConditionalService conditionalService2 = (ConditionalService) context.getBean("onClassConditionalService");
//		System.out.println(conditionalService2);
//
//		ConditionalService conditionalService3 = (ConditionalService) context.getBean("onPropertyConditionalService");
//		System.out.println(conditionalService3);
		
		context.close();
	}
}
