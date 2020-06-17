package com.spring.ioc.appliction.event.listener;

import com.spring.ioc.appliction.event.event.GenericTestEvent;
import org.springframework.context.ApplicationListener;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/20 16:59
 */
public class WildcardEventListener implements ApplicationListener<GenericTestEvent<?>> {

    @Override
    public void onApplicationEvent(GenericTestEvent<?> event) {
        System.out.println("-------event:"+event+"-------");
        System.out.println("-------WildcardEventListener-------");
    }
}
