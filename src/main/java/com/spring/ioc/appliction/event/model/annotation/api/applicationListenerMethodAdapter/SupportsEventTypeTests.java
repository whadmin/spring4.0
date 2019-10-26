package com.spring.ioc.appliction.event.model.annotation.api.applicationListenerMethodAdapter;

import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.ApplicationListenerMethodAdapter;
import org.springframework.core.ResolvableType;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

public class SupportsEventTypeTests extends AbstractApplicationEventListenerTests {

    private final SampleEvents sampleEvents = spy(new SampleEvents());

    @Test
    public void supportsEventTypeFormMethodParam1() {

        /** 获取SampleEvents类中handleRaw(ApplicationEvent event)方法 **/
        Method method = ReflectionUtils.findMethod(SampleEvents.class, "handleRaw", ApplicationEvent.class);

        /** 实例化ApplicationListenerMethodAdapter **/
        ApplicationListenerMethodAdapter adapter = new ApplicationListenerMethodAdapter("beanName", this.sampleEvents.getClass(), method);

        /** 支持 ApplicationEvent 事件  **/
        ResolvableType eventType = getGenericApplicationEventType("applicationEvent");
        assertThat(adapter.supportsEventType(eventType))
                .as("Wrong match for event '" + eventType + "' on " + method).isEqualTo(true);


        /** 支持 GenericTestEvent<String> extends ApplicationEvent 事件 **/
        ResolvableType eventType1 = getGenericApplicationEventType("wildcardEvent");
        assertThat(adapter.supportsEventType(eventType1))
                .as("Wrong match for event '" + eventType1 + "' on " + method).isEqualTo(true);


        /** 支持 GenericTestEvent<?> extends ApplicationEvent 事件 **/
        ResolvableType eventType2 = getGenericApplicationEventType("stringEvent");
        assertThat(adapter.supportsEventType(eventType2))
                .as("Wrong match for event '" + eventType2 + "' on " + method).isEqualTo(true);


        /** 支持 GenericTestEvent<Long> extends ApplicationEvent 事件 **/
        ResolvableType eventType3 = getGenericApplicationEventType("longEvent");
        assertThat(adapter.supportsEventType(eventType3))
                .as("Wrong match for event '" + eventType3 + "' on " + method).isEqualTo(true);


        /** 支持 GenericTestEvent<IllegalStateException> extends ApplicationEvent 事件 **/
        ResolvableType eventType4 = getGenericApplicationEventType("illegalStateExceptionEvent");
        assertThat(adapter.supportsEventType(eventType4))
                .as("Wrong match for event '" + eventType4 + "' on " + method).isEqualTo(true);


        /** 支持 GenericTestEvent<IOException> extends ApplicationEvent 事件 **/
        ResolvableType eventType5 = getGenericApplicationEventType("ioExceptionEvent");
        assertThat(adapter.supportsEventType(eventType5))
                .as("Wrong match for event '" + eventType5 + "' on " + method).isEqualTo(true);
    }

    /**
     * 使用@EventListener标注方法生成ApplicationListenerMethodAdapter，
     * 支持的事件类型ResolvableType从标注方法第一个参数获取
     */
    @Test
    public void supportsEventTypeFormMethodParam2() {

        /** 获取SampleEvents类中handleRaw(ApplicationEvent event)方法 **/
        Method method = ReflectionUtils.findMethod(SampleEvents.class, "handleGenericString", ApplicationEvent.class);

        /** 实例化ApplicationListenerMethodAdapter **/
        ApplicationListenerMethodAdapter adapter = new ApplicationListenerMethodAdapter("beanName", this.sampleEvents.getClass(), method);

        /** 不支持 ApplicationEvent 事件  **/
        ResolvableType eventType = getGenericApplicationEventType("applicationEvent");
        assertThat(adapter.supportsEventType(eventType))
                .as("Wrong match for event '" + eventType + "' on " + method).isEqualTo(false);


        /** 不支持 GenericTestEvent<Long> extends ApplicationEvent 事件 **/
        ResolvableType eventType3 = getGenericApplicationEventType("longEvent");
        assertThat(adapter.supportsEventType(eventType3))
                .as("Wrong match for event '" + eventType3 + "' on " + method).isEqualTo(false);

        /** 支持 GenericTestEvent<?> extends ApplicationEvent 事件 **/
        ResolvableType eventType2 = getGenericApplicationEventType("stringEvent");
        assertThat(adapter.supportsEventType(eventType2))
                .as("Wrong match for event '" + eventType2 + "' on " + method).isEqualTo(true);
    }

    /**
     * 使用@EventListener标注方法生成ApplicationListenerMethodAdapter，
     * 支持的事件类型ResolvableType从标注方法第一个参数获取
     */
    @Test
    public void supportsEventTypeFormMethodParam3() {

        /** 获取SampleEvents类中 handleString(String payload) 方法 **/
        Method method = ReflectionUtils.findMethod(SampleEvents.class, "handleString", String.class);
        /** 实例化ApplicationListenerMethodAdapter **/
        ApplicationListenerMethodAdapter adapter = new ApplicationListenerMethodAdapter("beanName", this.sampleEvents.getClass(), method);

        /** 不支持 ApplicationEvent 事件  **/
        ResolvableType eventType = getGenericApplicationEventType("applicationEvent");
        assertThat(adapter.supportsEventType(eventType))
                .as("Wrong match for event '" + eventType + "' on " + method).isEqualTo(false);


        /** 支持 PayloadApplicationEvent<String>  事件，广播会发出事件对象类型String，内部会转换为PayloadApplicationEvent<String> **/
        ResolvableType eventType3 = createGenericEventType(String.class);
        assertThat(adapter.supportsEventType(eventType3))
                .as("Wrong match for event '" + eventType3 + "' on " + method).isEqualTo(true);

        /** 不支持 PayloadApplicationEvent<Integer> 事件，广播会发出事件对象类型Integer，内部会转换为PayloadApplicationEvent<Integer> **/
        ResolvableType eventType2 = createGenericEventType(Integer.class);
        assertThat(adapter.supportsEventType(eventType2))
                .as("Wrong match for event '" + eventType2 + "' on " + method).isEqualTo(false);


        /** 支持 PayloadApplicationEvent 事件 **/
        assertThat(adapter.supportsEventType(ResolvableType.forClass(PayloadApplicationEvent.class)))
                .as("Wrong match for event '" + eventType2 + "' on " + method).isEqualTo(true);

        /** 支持 PayloadApplicationEvent 子类事件 **/
        assertThat(adapter.supportsEventType(ResolvableType.forClass(AbstractApplicationEventListenerTests.PayloadTestEvent.class)))
                .as("Wrong match for event '" + eventType2 + "' on " + method).isEqualTo(true);


        /** 支持 PayloadApplicationEvent 子类事件 **/
        assertThat(adapter.supportsEventType(ResolvableType.forClass(AbstractApplicationEventListenerTests.PayloadStringTestEvent.class)))
                .as("Wrong match for event '" + eventType2 + "' on " + method).isEqualTo(true);
    }


    @Test
    public void supportsEventTypeFormEventListenerAttr() {
        /** 获取指定类，指定方法名称对应Method对象，如果存在重载方法，返回第一个 **/
        Method method = ReflectionUtils.findMethod(SampleEvents.class, "handleStringOrInteger");
        /** 实例化ApplicationListenerMethodAdapter **/
        ApplicationListenerMethodAdapter adapter = new ApplicationListenerMethodAdapter("beanName", this.sampleEvents.getClass(), method);
        ResolvableType eventType = createGenericEventType(String.class);
        assertThat(adapter.supportsEventType(eventType))
                .as("Wrong match for event '" + eventType + "' on " + method).isEqualTo(true);

        ResolvableType eventType1 = createGenericEventType(Integer.class);
        assertThat(adapter.supportsEventType(eventType1))
                .as("Wrong match for event '" + eventType1 + "' on " + method).isEqualTo(true);
    }


    @Test
    public void invokeListenerMethodParam() {

        /** 获取SampleEvents类中handleRaw(ApplicationEvent event)方法 **/
        Method method = ReflectionUtils.findMethod(SampleEvents.class, "handleRaw", ApplicationEvent.class);

        /** 实例化ApplicationListenerMethodAdapter **/
        ApplicationListenerMethodAdapter adapter = new ApplicationListenerMethodAdapter("beanName", this.sampleEvents.getClass(), method);

        /** 支持 ApplicationEvent 事件  **/
        ResolvableType eventType = getGenericApplicationEventType("applicationEvent");

        adapter.onApplicationEvent(new AbstractApplicationEventListenerTests.GenericTestEvent<>(this, "test"));
    }



    private ResolvableType createGenericEventType(Class<?> payloadType) {
        return ResolvableType.forClassWithGenerics(PayloadApplicationEvent.class, payloadType);
    }




}