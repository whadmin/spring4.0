package com.spring.ioc.appliction.threadPoolTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.ioc.appliction.event.model.ContentEvent;
import com.spring.ioc.appliction.threadPoolTask.model.AsyncTaskService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:taskExecutor/taskExecutorConfig2.xml" })
public class TaskExecutorForXml2 {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void testPublishEvent() {
		AsyncTaskService asyncTaskService = applicationContext
				.getBean(AsyncTaskService.class);

		for (int i = 0; i < 10; i++) {
			asyncTaskService.executeAsyncTask(i);
			//asyncTaskService.executeAsyncTaskPlus(i);
		}
		System.out.println("over: ");
	}
}
