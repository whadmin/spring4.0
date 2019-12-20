package com.spring.ioc.appliction.event_new.listener;

import com.spring.ioc.appliction.event_new.event.GenericTestEvent;
import org.springframework.context.ApplicationListener;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/20 15:37
 */
public class LongEventListener implements ApplicationListener<GenericTestEvent<Long>> {

    @Override
    public void onApplicationEvent(GenericTestEvent<Long> event) {
        System.out.println("-------event:"+event+"-------");
        System.out.println("-------LongEventListener-------");
    }
}