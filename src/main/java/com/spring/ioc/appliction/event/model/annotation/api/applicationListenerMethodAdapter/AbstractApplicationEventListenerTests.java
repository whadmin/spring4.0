package com.spring.ioc.appliction.event.model.annotation.api.applicationListenerMethodAdapter;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.core.ResolvableType;
import java.io.IOException;

public abstract class AbstractApplicationEventListenerTests {

    protected ResolvableType getGenericApplicationEventType(String fieldName) {
        try {
            return ResolvableType.forField(AbstractApplicationEventListenerTests.TestEvents.class.getField(fieldName));
        }
        catch (NoSuchFieldException ex) {
            throw new IllegalStateException("No such field on Events '" + fieldName + "'");
        }
    }

    public static class TestEvents {

        public ApplicationEvent applicationEvent;

        public AbstractApplicationEventListenerTests.GenericTestEvent<?> wildcardEvent;

        public AbstractApplicationEventListenerTests.GenericTestEvent<String> stringEvent;

        public AbstractApplicationEventListenerTests.GenericTestEvent<Long> longEvent;

        public AbstractApplicationEventListenerTests.GenericTestEvent<IllegalStateException> illegalStateExceptionEvent;

        public AbstractApplicationEventListenerTests.GenericTestEvent<IOException> ioExceptionEvent;
    }

    public static class GenericTestEvent<T> extends ApplicationEvent {

        private final T payload;

        public GenericTestEvent(Object source, T payload) {
            super(source);
            this.payload = payload;
        }
        public T getPayload() {
            return this.payload;
        }
    }

    @SuppressWarnings({"unused", "serial"})
    public static class PayloadTestEvent<V, T> extends PayloadApplicationEvent<T> {

        private final V something;

        public PayloadTestEvent(Object source, T payload, V something) {
            super(source, payload);
            this.something = something;
        }
    }


    @SuppressWarnings({ "serial" })
    public static class PayloadStringTestEvent extends AbstractApplicationEventListenerTests.PayloadTestEvent<Long, String> {
        public PayloadStringTestEvent(Object source, String payload, Long something) {
            super(source, payload, something);
        }
    }
}
