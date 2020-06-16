package com.spring.common.annotation.annotatedElementUtils;

import com.spring.common.annotation.model.AnnotatedModel;
import org.junit.Test;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.reflect.AnnotatedElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.core.annotation.AnnotatedElementUtils.hasAnnotation;

/**
 * hasAnnotation：用来判断{@link AnnotatedElement}是否被指定注解类型annotationType修饰
 * <p>
 * 相对于isAnnotated方法，无论是否父类的注解是否被@Inherited元注解的注解修饰，会获取父类所有的注解
 * <p>
 * 当{@link AnnotatedElement}用来表示Class类时，isAnnotated获取其修饰注解包括：
 * <p>
 * 1  该Class类上的修饰的注解
 * 2  该Class类上的修饰的注解同时也包括元注解
 * 3  该Class父类上的注解,无论是否父类的注解是否被@Inherited元注解的注解修饰（相对于isAnnotated扩展）
 * 4  该Class父类上的注解的元注解，无论是否父类的注解是否被@Inherited元注解的注解修饰（相对于isAnnotated扩展）
 * 5  该Class实现接口的注解（相对于isAnnotated扩展）
 * 5  该Class实现接口的注解的元注解（相对于isAnnotated扩展）
 * 当{@link AnnotatedElement}用来表示接口时，isAnnotationPresent获取其修饰注解包括：
 * <p>
 * 1 该interface上的修饰的注解
 * 2 该interface类上的修饰的注解同时也包括元注解（相对于JDKisAnnotationPresent改进）
 * 3 包含父接口上的注解（无论是否标注@Inherited元注解）
 * 4 包含父接口上的注解的元注解（无论是否标注@Inherited元注解）
 */
public class HasAnnotationTest {


    /**
     * hasAnnotation 方法会获取该Class类上的修饰的注解
     */
    @Test
    public void hasAnnotationOnNonAnnotatedClass() {
        assertThat(hasAnnotation(AnnotatedModel.NonAnnotatedClass.class, AnnotatedModel.Transactional.class)).isFalse();
    }

    /**
     * hasAnnotation 方法会获取该Class类上的修饰的注解同时也包括元注解（相对于JDKisAnnotationPresent改进）
     */
    @Test
    public void hasAnnotationOnClassMetaAnnotation() {
        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalClass.class, Component.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalClass.class, Indexed.class)).isTrue();

        assertThat(hasAnnotation(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedComposedTransactionalInherited.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedComposedTransactionalClass.class, Component.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedComposedTransactionalClass.class, Indexed.class)).isTrue();
    }

    /**
     * hasAnnotation 方法会获取该Class父类上的注解,父类上的注解的元注解(无论是否父类的注解是否被@Inherited元注解的注解修饰)
     * 相对于isAnnotated方法，无论是否父类的注解是否被@Inherited元注解的注解修饰，会获取父类所有的注解
     */
    @Test
    public void hasAnnotationOnClassWithMetaDepth() {
        assertThat(hasAnnotation(AnnotatedModel.SubComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.SubComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.SubComposedTransactionalClass.class, Component.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.SubComposedTransactionalClass.class, Indexed.class)).isTrue();
    }

    @Test
    public void hasAnnotationForPlainTypes() {
        assertThat(hasAnnotation(NonNullApi.class, Documented.class)).isTrue();
        assertThat(hasAnnotation(NonNullApi.class, Nonnull.class)).isTrue();
    }

    /**
     * hasAnnotation {@link AnnotatedElement}用来表示Class类时，包含了实现接口的注解以及元注解
     */
    @Test
    public void hasAnnotationOnClassNOIncludeInterfaceAnnotated() {
        assertThat(hasAnnotation(AnnotatedModel.implementComposedTransactionalInterfaceClass.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.implementComposedTransactionalInterfaceClass.class, AnnotatedModel.ComposedComposedTransactionalInherited.class)).isTrue();

        assertThat(hasAnnotation(AnnotatedModel.implementComposedTransactionalInterfaceClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.implementComposedTransactionalInterfaceClass.class, Component.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.implementComposedTransactionalInterfaceClass.class, Indexed.class)).isTrue();
    }

    /**
     * hasAnnotation {@link AnnotatedElement}用来表示接口时，包含了接口的注解
     */
    @Test
    public void hasAnnotationOnInterfaceDirectAnnotated() {
        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalInterface.class, AnnotatedModel.ComposedComposedTransactionalInherited.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalInterface.class, AnnotatedModel.ComposedTransactional.class)).isTrue();

        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalInterface.class, AnnotatedModel.ComposedComposedTransactionalInherited.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalInterface.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
    }

    /**
     * hasAnnotation {@link AnnotatedElement}用来表示接口时，包含了标注在接口上的注解的元注解
     */
    @Test
    public void hasAnnotationOnInterfaceNotIncludedMetaAnnotation() {
        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalInterface.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalInterface.class, Component.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.ComposedTransactionalInterface.class, Indexed.class)).isTrue();
    }

    /**
     * hasAnnotation {@link AnnotatedElement}用来表示接口时，包含了父接口上的注解
     */
    @Test
    public void hasAnnotationOnInterfacetIncludeSuperInterfaceAnnotated() {
        assertThat(hasAnnotation(AnnotatedModel.SubComposedTransactionalInterface.class, AnnotatedModel.ComposedComposedTransactionalInherited.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.SubComposedTransactionalInterface.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
    }

    /**
     * hasAnnotation {@link AnnotatedElement}用来表示接口时，包含了父接口上的注解的元注解
     */
    @Test
    public void hasAnnotationOnInterfacetIncludeSuperInterfaceMetaAnnotated() {
        assertThat(hasAnnotation(AnnotatedModel.SubComposedTransactionalInterface.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.SubComposedTransactionalInterface.class, Component.class)).isTrue();
        assertThat(hasAnnotation(AnnotatedModel.SubComposedTransactionalInterface.class, Indexed.class)).isTrue();
    }
}
