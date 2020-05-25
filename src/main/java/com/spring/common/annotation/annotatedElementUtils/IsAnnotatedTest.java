package com.spring.common.annotation.annotatedElementUtils;

import com.spring.common.annotation.model.AnnotatedModel;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.lang.reflect.AnnotatedElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.core.annotation.AnnotatedElementUtils.isAnnotated;

/**
 *
 * isAnnotated：用来判断{@link AnnotatedElement}是否被指定注解类型annotationType修饰
 *
 * 相对于JDK isAnnotationPresent方法，将注解的元注解添加到修饰{@link AnnotatedElement}的范围
 *
 * 当{@link AnnotatedElement}用来表示Class类时，isAnnotated获取其修饰注解包括：
 *
 *   1  该Class类上的修饰的注解
 *   2  该Class类上的修饰的注解同时也包括元注解（相对于JDKisAnnotationPresent改进）
 *   3  该Class父类上标注了@Inherited元注解的注解
 *   4  该Class父类上标注了@Inherited元注解的注解的元注解（相对于JDKisAnnotationPresent改进）
 */
public class IsAnnotatedTest {

    /**
     * isAnnotated 方法会获取该Class类上的修饰的注解
     */
    @Test
    public void isAnnotatedOnNonAnnotatedClass() {
        assertThat(isAnnotated(AnnotatedModel.NonAnnotatedClass.class, AnnotatedModel.Transactional.class)).isFalse();
        assertThat(isAnnotated(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedComposedTransactionalInherited.class)).isTrue();
    }

    /**
     * isAnnotated 方法会获取该Class类上的修饰的注解同时也包括元注解（相对于JDKisAnnotationPresent改进）
     */
    @Test
    public void isAnnotatedOnClassMetaAnnotation() {
        assertThat(isAnnotated(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedTransactionalClass.class, Component.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedTransactionalClass.class, Indexed.class)).isTrue();

        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, Component.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, Indexed.class)).isTrue();
    }

    /**
     * isAnnotated 方法会获取该Class父类上标注了@Inherited元注解的注解的元注解
     */
    @Test
    public void isAnnotatedOnClassWithMetaDepth() {
        assertThat(isAnnotated(AnnotatedModel.SubComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isFalse();
        assertThat(isAnnotated(AnnotatedModel.SubComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isFalse();
        assertThat(isAnnotated(AnnotatedModel.SubComposedTransactionalClass.class, Component.class)).isFalse();
        assertThat(isAnnotated(AnnotatedModel.SubComposedTransactionalClass.class, Indexed.class)).isFalse();

        assertThat(isAnnotated(AnnotatedModel.SubComposedComposedTransactionalClass.class, AnnotatedModel.ComposedComposedTransactionalInherited.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.SubComposedComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.SubComposedComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.SubComposedComposedTransactionalClass.class, Component.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.SubComposedComposedTransactionalClass.class, Indexed.class)).isTrue();
    }
}
