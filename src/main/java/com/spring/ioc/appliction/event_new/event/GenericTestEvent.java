package com.spring.ioc.appliction.event_new.event;

import org.springframework.context.ApplicationEvent;

public class GenericTestEvent<T> extends ApplicationEvent {

    private final T payload;

    public GenericTestEvent(Object source, T payload) {
        super(source);
        this.payload = payload;
    }

    public T getPayload() {
        return this.payload;
    }
}

class StringEvent extends GenericTestEvent<String> {

    public StringEvent(Object source, String payload) {
        super(source, payload);
    }
}

class LongEvent extends GenericTestEvent<Long> {

    public LongEvent(Object source, Long payload) {
        super(source, payload);
    }
}
