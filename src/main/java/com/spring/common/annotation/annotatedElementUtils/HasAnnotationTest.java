package com.spring.common.annotation.annotatedElementUtils;

import com.spring.common.annotation.model.AnnotatedModel;
import org.junit.Test;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.core.annotation.AnnotatedElementUtils.hasAnnotation;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/24 15:35
 */
public class HasAnnotationTest {

    private static final String TX_NAME = AnnotatedModel.Transactional.class.getName();



    private Set<String> names(Class<?>... classes) {
        return stream(classes).map(Class::getName).collect(toSet());
    }











    /**
     * 判断指定的{@link AnnotatedElement}是否被指定注解修饰（也包括注解上的元注解）
     *
     * 如果{@link AnnotatedElement}类型为Class,修饰注解包括Class上的修饰的注解(及其元注解)，包括其祖先类Class上的修饰的注解(及其元注解)
     * 如果{@link AnnotatedElement}类型为Annotation，修饰注解包括为其上的元注解
     *
     */
    @Test
    public void hasAnnotationTest(){
        //NonAnnotatedClass类没有被注解修饰，因此不被Transactional注解修饰
        hasAnnotationOnNonAnnotatedClass();
        //isAnnotated 判断Class是否被指定注解修饰包含了Class上的修饰的注解(及其元注解)
        hasAnnotationOnClassMetaAnnotation();
        //isAnnotated 判断Annotation是否被指定注解修饰包含了其祖先类Class上的修饰的注解(及其元注解)
        hasAnnotationOnClassWithMetaDepth();
        //isAnnotated 判断Class是否被指定注解修饰包含了其上的元注解
        hasAnnotationForPlainTypes();
    }


    @Test
    public void hasAnnotationOnNonAnnotatedClass() {
        assertThat(hasAnnotation(AnnotatedModel.NonAnnotatedClass.class, AnnotatedModel.Transactional.class)).isFalse();
    }

    @Test
    public void hasAnnotationOnClassMetaAnnotation() {
        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalClass.class, Component.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalClass.class, Indexed.class)).isTrue();

        assertThat(hasAnnotation(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedComposedTransactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedComposedTransactionalClass.class, Component.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedComposedTransactionalClass.class, Indexed.class)).isTrue();
    }

    @Test
    public void hasAnnotationOnClassWithMetaDepth() {
        assertThat(hasAnnotation(AnnotatedModel.SubTransactionalComponentClass.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.SubTransactionalComponentClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.SubTransactionalComponentClass.class, Component.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.SubTransactionalComponentClass.class, Indexed.class)).isTrue();
    }

    @Test
    public void hasAnnotationForPlainTypes() {
        assertThat(hasAnnotation(NonNullApi.class, Documented.class)).isTrue();
        assertThat(hasAnnotation(NonNullApi.class, Nonnull.class)).isTrue();
    }
}
