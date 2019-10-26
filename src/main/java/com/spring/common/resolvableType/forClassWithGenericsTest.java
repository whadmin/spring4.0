package com.spring.common.resolvableType;

import org.junit.Test;
import org.springframework.core.ResolvableType;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class forClassWithGenericsTest {

    @Test
    void forClassWithGenerics() throws Exception {
        ResolvableType elementType = ResolvableType.forClassWithGenerics(Map.class, Integer.class, String.class);
        ResolvableType listType = ResolvableType.forClassWithGenerics(List.class, elementType);
        assertThat(listType.toString()).isEqualTo("java.util.List<java.util.Map<java.lang.Integer, java.lang.String>>");
        assertThat(listType.getType().getTypeName()).isEqualTo("java.util.List<java.util.Map<java.lang.Integer, java.lang.String>>");
        assertThat(listType.getType().toString()).isEqualTo("java.util.List<java.util.Map<java.lang.Integer, java.lang.String>>");
    }
}
