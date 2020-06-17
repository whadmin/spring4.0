package com.spring.ioc.appliction.event.listener;

import com.spring.ioc.appliction.event.event.GenericTestEvent;
import org.springframework.context.ApplicationListener;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/20 15:08
 *
 * 仅只支持GenericTestEvent<String> 事件监听器
 * 不支持GenericTestEvent<?> 等事件类（Class）相同但类型（ResolvableType）不同的事件
 */
public class StringEventListener implements ApplicationListener<GenericTestEvent<String>> {

    @Override
    public void onApplicationEvent(GenericTestEvent<String> event) {
        System.out.println("-------event:"+event+"-------");
        System.out.println("-------StringEventListener-------");
    }
}