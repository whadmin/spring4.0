package com.spring.ioc.appliction.event.listener;

import com.spring.ioc.appliction.event.event.CustomizeEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/20 23:15
 */
public class CustomizeGenericApplicationListener implements GenericApplicationListener {

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        ResolvableType declaredEventType = ResolvableType.forClass(CustomizeEvent.class);
        return declaredEventType.isAssignableFrom(eventType);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("-------event:" + event + "-------");
        System.out.println("-------CustomizeGenericApplicationListener-------");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
