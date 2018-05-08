package com.spring.ioc.appliction.scheduler.model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskService {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000) 
    /** 通过@Scheduled声明该方法是计划任务，使用fixedRate属性每隔固定时间执行 **/
    public void execute1(){
        System.out.println("execute1 每隔5秒执行一次 "+dateFormat.format(new Date()));
    }

    @Scheduled(cron = "15,30,45 * * * * ?" ) 
    public void execute2(){
        System.out.println("execute2 在指定时间 "+dateFormat.format(new Date())+" 执行");
    }
    
    public void execute3(){
        System.out.println("execute3 每隔5秒执行一次 "+dateFormat.format(new Date())+" 执行");
    } 
    
    public void execute4(){
        System.out.println("execute4 在指定时间 "+dateFormat.format(new Date())+" 执行");
    } 
}
