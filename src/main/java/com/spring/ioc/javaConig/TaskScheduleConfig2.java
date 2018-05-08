package com.spring.ioc.javaConig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
@ComponentScan("com.spring.ioc.appliction.scheduler.model")
/** @EnableScheduling 注解，Spring 会自动的创建基于ThreadPoolTaskScheduler  实例注入到上下文中 **/
public class TaskScheduleConfig2 implements SchedulingConfigurer{

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(new ThreadPoolTaskScheduler());
        taskRegistrar.getScheduler().schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        }, new CronTrigger("*/5 * * * * ?"));
    }
}
