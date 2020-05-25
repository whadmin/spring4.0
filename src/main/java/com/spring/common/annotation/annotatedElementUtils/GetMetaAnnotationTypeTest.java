package com.spring.common.annotation.annotatedElementUtils;

import com.spring.common.annotation.model.AnnotatedModel;
import org.junit.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.lang.reflect.AnnotatedElement;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.core.annotation.AnnotatedElementUtils.getMetaAnnotationTypes;

/**
 * getMetaAnnotationType：用来获取{@link AnnotatedElement}装饰注解的元注解
 *
 * 要知道装饰注解的元注解，首先需要知道hasAnnotation能够获取该{@link AnnotatedElement}注解的范围
 *
 * 当{@link AnnotatedElement}用来表示Class类时，获取该{@link AnnotatedElement}注解的范围和{@link AnnotatedElementUtils#isAnnotated}一致包括：
 *
 *   1  该Class类上的修饰的注解
 *   2  该Class类上的修饰的注解同时也包括元注解
 *   3  该Class父类上标注了@Inherited元注解的注解
 *   4  该Class父类上标注了@Inherited元注解的注解的元注解
 */
public class GetMetaAnnotationTypeTest {


    /**
     * 类是没有修饰注解，更不存在元注解
     */
    @Test
    public void getMetaAnnotationTypesOnNonAnnotatedClass() {
        assertThat(getMetaAnnotationTypes(AnnotatedModel.NonAnnotatedClass.class, AnnotatedModel.ComposedTransactional.class).isEmpty()).isTrue();
    }

    /**
     * 获取类上注解注解的元注解
     */
    @Test
    public void getMetaAnnotationTypesOnClassWithMetaDepth1() {
        Set<String> names = getMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class);
        assertThat(names).isEqualTo(names(AnnotatedModel.Transactional.class, Component.class, Indexed.class));
    }

    @Test
    public void getMetaAnnotationTypesOnClassWithMetaDepth2() {
        Set<String> names = getMetaAnnotationTypes(AnnotatedModel.SubComposedComposedTransactionalClass.class, AnnotatedModel.ComposedComposedTransactionalInherited.class);
        assertThat(names).isEqualTo(names(AnnotatedModel.ComposedTransactional.class, AnnotatedModel.Transactional.class, Component.class, Indexed.class));

        names = getMetaAnnotationTypes(AnnotatedModel.SubComposedTransactionalClass.class, AnnotatedModel.ComposedComposedTransactionalInherited.class.getName());
        assertThat(names).isEmpty();
    }

    private Set<String> names(Class<?>... classes) {
        return stream(classes).map(Class::getName).collect(toSet());
    }
}
