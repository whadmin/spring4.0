package com.spring.common.annotation.annotatedElementUtils;

import com.spring.common.annotation.model.AnnotatedModel;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.core.annotation.AnnotatedElementUtils.hasMetaAnnotationTypes;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/24 15:33
 */
public class HasMetaAnnotationTypesTest {

    /**
     * 判断指定类是否存在指定元注解修饰
     */
    @Test
    public void hasMetaAnnotationTypesTest() {
        //判断NonAnnotatedClass类没有被注解修饰，更不存在元注解修饰
        hasMetaAnnotationTypesOnNonAnnotatedClass();
        //判断TransactionalComponentClass类被TransactionalComponent注解修饰，但其并不是元注解
        hasMetaAnnotationTypesOnClassWithMetaDepth0();
        //判断TransactionalComponentClass类是否存在指定元注解修饰
        hasMetaAnnotationTypesOnClassWithMetaDepth1();
        //判断TransactionalComponentClass类是否存在指定元注解修饰
        hasMetaAnnotationTypesOnClassWithMetaDepth2();
    }


    @Test
    public void hasMetaAnnotationTypesOnNonAnnotatedClass() {
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.NonAnnotatedClass.class, AnnotatedModel.Transactional.class.getName())).isFalse();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.NonAnnotatedClass.class, AnnotatedModel.Transactional.class)).isFalse();
    }


    @Test
    public void hasMetaAnnotationTypesOnClassWithMetaDepth0() {
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class.getName())).isFalse();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isFalse();
    }

    @Test
    public void hasMetaAnnotationTypesOnClassWithMetaDepth1() {
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, Component.class.getName())).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.Transactional.class.getName())).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, Indexed.class.getName())).isTrue();

        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, Component.class)).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, Indexed.class)).isTrue();
    }

    @Test
    public void hasMetaAnnotationTypesOnClassWithMetaDepth2() {
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedComposedTransactional.class.getName())).isFalse();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class.getName())).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, Component.class.getName())).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.Transactional.class.getName())).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, Indexed.class.getName())).isTrue();

        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedComposedTransactional.class)).isFalse();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, Component.class)).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, Indexed.class)).isTrue();
    }


    private Set<String> names(Class<?>... classes) {
        return stream(classes).map(Class::getName).collect(toSet());
    }
}
