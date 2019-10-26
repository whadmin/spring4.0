package com.spring.common.resolvableType;

import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class IsAssignableFromTest {

    @Test
    public void isAssignableFromForNone() throws Exception {
        ResolvableType objectType = ResolvableType.forClass(Object.class);
        assertThat(objectType.isAssignableFrom(ResolvableType.NONE)).isEqualTo(false);
        assertThat(ResolvableType.NONE.isAssignableFrom(objectType)).isEqualTo(false);
    }

    @Test
    public void isAssignableFromForObjext() throws Exception {
        ResolvableType objectType = ResolvableType.forClass(ApplicationEvent.class);
        assertThat(objectType.isAssignableFrom(getGenericApplicationEventType("applicationEvent"))).isEqualTo(true);
        assertThat(objectType.isAssignableFrom(getGenericApplicationEventType("wildcardEvent"))).isEqualTo(true);
        assertThat(objectType.isAssignableFrom(getGenericApplicationEventType("stringEvent"))).isEqualTo(true);
        assertThat(objectType.isAssignableFrom(getGenericApplicationEventType("longEvent"))).isEqualTo(true);
        assertThat(objectType.isAssignableFrom(getGenericApplicationEventType("illegalStateExceptionEvent"))).isEqualTo(true);
        assertThat(objectType.isAssignableFrom(getGenericApplicationEventType("ioExceptionEvent"))).isEqualTo(true);
    }


    protected ResolvableType getGenericApplicationEventType(String fieldName) {
        try {
            return ResolvableType.forField(TestEvents.class.getField(fieldName));
        }
        catch (NoSuchFieldException ex) {
            throw new IllegalStateException("No such field on Events '" + fieldName + "'");
        }
    }

    protected static class GenericTestEvent<T> extends ApplicationEvent {

        private final T payload;

        public GenericTestEvent(Object source, T payload) {
            super(source);
            this.payload = payload;
        }

        public T getPayload() {
            return this.payload;
        }
    }

    static class TestEvents {

        public ApplicationEvent applicationEvent;

        public GenericTestEvent<?> wildcardEvent;

        public GenericTestEvent<String> stringEvent;

        public GenericTestEvent<Long> longEvent;

        public GenericTestEvent<IllegalStateException> illegalStateExceptionEvent;

        public GenericTestEvent<IOException> ioExceptionEvent;
    }
}
