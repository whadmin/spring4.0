/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.spring.ioc.appliction.event.model;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;


/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-8 下午9:10
 * <p>Version: 1.0
 */

public class HelloWoldListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(final ApplicationEvent event) {
        if(event instanceof ContentEvent) {
            System.out.println("Hello Wold收到了新的内容：" + event.getSource());
        }
    }
}
