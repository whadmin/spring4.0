package com.spring.ioc.appliction.event_new.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/20 23:07
 */
public class CustomizeEvent extends ApplicationEvent {

    public CustomizeEvent(Object source) {
        super(source);
    }
}
