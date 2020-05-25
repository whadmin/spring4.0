package com.spring.common.annotation.annotatedElementUtils;

import com.spring.common.annotation.model.AnnotatedModel;
import org.junit.Test;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.reflect.AnnotatedElement;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.core.annotation.AnnotatedElementUtils.hasAnnotation;

/**
 * hasAnnotation：用来判断{@link AnnotatedElement}是否被指定注解类型annotationType修饰
 * <p>
 * 相对于JDK isAnnotationPresent方法，将注解的元注解添加到修饰{@link AnnotatedElement}的范围
 * 相对于isAnnotated方法，无论是否父类的注解是否被@Inherited元注解的注解修饰，会获取父类所有的注解
 * <p>
 * 当{@link AnnotatedElement}用来表示Class类时，isAnnotated获取其修饰注解包括：
 * <p>
 * 1  该Class类上的修饰的注解
 * 2  该Class类上的修饰的注解同时也包括元注解（相对于JDKisAnnotationPresent改进）
 * 3  该Class父类上的注解
 * 4  该Class父类上的注解的元注解（相对于JDKisAnnotationPresent改进）
 */
public class HasAnnotationTest {

    private static final String TX_NAME = AnnotatedModel.Transactional.class.getName();


    private Set<String> names(Class<?>... classes) {
        return stream(classes).map(Class::getName).collect(toSet());
    }

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
}
