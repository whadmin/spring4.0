package com.spring.ioc.appliction.conditional;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;



import com.spring.ioc.appliction.conditional.model.ConditionalService;
import com.spring.ioc.appliction.conditional.model.ListService;
import com.spring.ioc.javaConig.ConditionConifg;

public class ConditionalForJavaConfig {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConditionConifg.class);
		
		ListService listService = context.getBean(ListService.class);
		
		System.out.println(context.getEnvironment().getProperty("os.name") + "系统下的列表命令为: " + listService.showListCmd());
		
		
		ConditionalService conditionalService1 = (ConditionalService) context.getBean("onBeanConditionalService");
		System.out.println(conditionalService1);
		
		ConditionalService conditionalService2 = (ConditionalService) context.getBean("onClassConditionalService");
		System.out.println(conditionalService2);
		
		ConditionalService conditionalService3 = (ConditionalService) context.getBean("onPropertyConditionalService");
		System.out.println(conditionalService3);
		
		context.close();
	}
}
