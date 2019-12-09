package com.spring.ioc.appliction.scheduler;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.spring.ioc.javaConig.TaskScheduleConfig;
import com.spring.ioc.javaConig.TaskScheduleConfig2;

public class SchedulerForJavaConfig {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				TaskScheduleConfig.class);
		
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
//				TaskScheduleConfig2.class);
	}

}
