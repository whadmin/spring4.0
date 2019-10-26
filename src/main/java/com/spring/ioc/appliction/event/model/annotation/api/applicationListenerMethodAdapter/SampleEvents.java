package com.spring.ioc.appliction.event.model.annotation.api.applicationListenerMethodAdapter;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

public class SampleEvents {

    @EventListener
    public void handleRaw(ApplicationEvent event) {
    }

    @EventListener
    public void handleGenericString(AbstractApplicationEventListenerTests.GenericTestEvent<String> event) {
    }

    @EventListener
    public void handleString(String payload) {
    }

    @EventListener({String.class, Integer.class})
    public void handleStringOrInteger() {
    }
}
