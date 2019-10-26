package com.spring.common.reflectionUtils;

import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionUtilsTests {

    @Test
    public  void getAllDeclaredMethods() throws Exception {
        class Foo {
            @Override
            public String toString() {
                return super.toString();
            }
        }
        int toStringMethodCount = 0;
        for (Method method : ReflectionUtils.getAllDeclaredMethods(Foo.class)) {
            if (method.getName().equals("toString")) {
                toStringMethodCount++;
            }
            System.out.println(method.getName());
        }
        assertThat(toStringMethodCount).isEqualTo(2);
    }
}
