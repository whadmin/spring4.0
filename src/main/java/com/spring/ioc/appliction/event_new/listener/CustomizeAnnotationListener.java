package com.spring.ioc.appliction.event_new.listener;

import com.spring.ioc.appliction.event_new.event.CustomizeEvent;
import org.springframework.context.event.EventListener;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/24 12:31
 */
public class CustomizeAnnotationListener {

    @EventListener
    public void customizeEvent(CustomizeEvent event) {
        System.out.println("-------event:"+event+"-------");
        System.out.println("-------CustomizeAnnotationListener:customizeEvent_method_handle-------");
    }
}
