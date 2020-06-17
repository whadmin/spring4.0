package com.spring.ioc.appliction.async;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.spring.ioc.appliction.async.model.AsyncTaskService;
import com.spring.ioc.javaConig.TaskExecutorConfig;

public class TaskExecutorForJavaConfig {
	
	public static void main(String[] args) {
		 AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TaskExecutorConfig.class);
		 
		 AsyncTaskService asyncTaskService = context.getBean(AsyncTaskService.class);
		 
		 for(int i =0 ;i<10;i++){
			 asyncTaskService.executeAsyncTask(i);
			 asyncTaskService.executeAsyncTaskPlus(i);
	        }
		 System.out.println("over: ");
	        context.close();
	}

}
