package com.spring.ioc.appliction.event.model.annotation.api.applicationListenerMethodAdapter;

import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ApplicationListenerMethodAdapter;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static org.mockito.Mockito.spy;

public class OnApplicationEventTest extends AbstractApplicationEventListenerTests {

    private final SampleEvents sampleEvents = spy(new SampleEvents());


    @Test
    public void onApplicationEvent1() {
        /** 获取SampleEvents类中handleRaw(ApplicationEvent event)方法 **/
        Method method = ReflectionUtils.findMethod(SampleEvents.class, "handleRaw", ApplicationEvent.class);

        GenericTestEvent<String> event = createGenericTestEvent("test");


        /** 实例化ApplicationListenerMethodAdapter **/
        ApplicationListenerMethodAdapter adapter = new ApplicationListenerMethodAdapter("beanName", this.sampleEvents.getClass(), method);

        adapter.onApplicationEvent(event);
    }


    protected <T> GenericTestEvent<T> createGenericTestEvent(T payload) {
        return new GenericTestEvent<>(this, payload);
    }

    private ApplicationListenerMethodAdapter createTestInstance(Method method) {
        return new StaticApplicationListenerMethodAdapter(method, this.sampleEvents);
    }

    private static class StaticApplicationListenerMethodAdapter extends ApplicationListenerMethodAdapter {

        private final Object targetBean;

        public StaticApplicationListenerMethodAdapter(Method method, Object targetBean) {
            super("unused", targetBean.getClass(), method);
            this.targetBean = targetBean;
        }

        @Override
        public Object getTargetBean() {
            return this.targetBean;
        }
    }
}
