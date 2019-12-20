package com.spring.ioc.appliction.event_new.listener;

import com.spring.ioc.appliction.event_new.event.CustomizeEvent;
import org.springframework.context.ApplicationListener;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/20 23:09
 */
public class CustomizeApplicationListener implements ApplicationListener<CustomizeEvent> {

    @Override
    public void onApplicationEvent(final CustomizeEvent event) {
        System.out.println("-------event:"+event+"-------");
        System.out.println("-------CustomizeApplicationListener-------");
    }
}
