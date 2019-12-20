/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.spring.ioc.appliction.event_new.listener;

import com.spring.ioc.appliction.event.model.ContentEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;


/**
 * @Author: wuhao.w
 * @Date: 2019/12/20 15:08
 *
 * 支持所有ApplicationEvent 事件监听器（包括所有ApplicationEvent 子类）
 */
public class HelloWoldListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(final ApplicationEvent event) {
        System.out.println("-------event:"+event+"-------");
        System.out.println("-------HelloWoldListener-------");
    }
}
