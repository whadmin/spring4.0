package com.spring.ioc.appliction.scheduler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SchedulerForXml {
	
	   public static void main(String[] args) {
	        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:ioc/application/scheduler/taskScheduleConfig.xml");
	    }

}
