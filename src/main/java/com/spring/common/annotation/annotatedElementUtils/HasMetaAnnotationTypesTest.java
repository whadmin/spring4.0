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
import static org.springframework.core.annotation.AnnotatedElementUtils.hasMetaAnnotationTypes;

/**
 * hasAnnotation：用来判断{@link AnnotatedElement}是否被指定元注解类型annotationType修饰
 *
 * 要知道是否能被指定的元注解修饰，首先需要知道hasAnnotation能够获取该{@link AnnotatedElement}注解的范围
 *
 * 当{@link AnnotatedElement}用来表示Class类时，获取该{@link AnnotatedElement}注解的范围和{@link AnnotatedElementUtils#isAnnotated}一致包括：
 *
 *   1  该Class类上的修饰的注解
 *   2  该Class类上的修饰的注解同时也包括元注解（相对于JDKisAnnotationPresent改进）
 *   3  该Class父类上标注了@Inherited元注解的注解
 *   4  该Class父类上标注了@Inherited元注解的注解的元注解（相对于JDKisAnnotationPresent改进）
 */
public class HasMetaAnnotationTypesTest {

    /**
     * 类是没有修饰注解，更不存在元注解
     */
    @Test
    public void hasMetaAnnotationTypesOnNonAnnotatedClass() {
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.NonAnnotatedClass.class, AnnotatedModel.Transactional.class.getName())).isFalse();
    }

    /**
     * 获取类上注解注解的元注解
     */
    @Test
    public void hasMetaAnnotationTypesOnAnnotatedClass() {
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isFalse();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, Component.class)).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, Indexed.class)).isTrue();

        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedComposedTransactionalInherited.class)).isFalse();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, Component.class)).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, Indexed.class)).isTrue();
    }

    /**
     * 获取获取该Class父类上标注了@Inherited元注解的注解的元注解
     */
    @Test
    public void hasMetaAnnotationTypesOnClassWithMetaDepth0() {
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.SubComposedTransactionalClass.class, Component.class.getName())).isFalse();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.SubComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isFalse();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.SubComposedTransactionalClass.class, Indexed.class)).isFalse();

        assertThat(hasMetaAnnotationTypes(AnnotatedModel.SubComposedComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class.getName())).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.SubComposedComposedTransactionalClass.class, Component.class.getName())).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.SubComposedComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(hasMetaAnnotationTypes(AnnotatedModel.SubComposedComposedTransactionalClass.class, Indexed.class)).isTrue();
    }



    private Set<String> names(Class<?>... classes) {
        return stream(classes).map(Class::getName).collect(toSet());
    }
}
