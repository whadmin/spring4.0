package com.spring.ioc.javaConig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("com.spring.ioc.appliction.scheduler.model")
@EnableScheduling
/** @EnableScheduling 注解，Spring 会自动的创建基于ThreadPoolTaskScheduler  实例注入到上下文中 **/
public class TaskScheduleConfig {

}
