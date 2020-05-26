package com.spring.common.annotation.jdkAnnotatedElement;

import com.spring.common.annotation.model.AnnotatedModel;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.AnnotatedElement;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * isAnnotationPresent 用来判断{@link AnnotatedElement}是否被指定注解类型annotationType修饰
 * <p>
 * 当{@link AnnotatedElement}用来表示Class类时，isAnnotationPresent获取其修饰注解包括：
 * <p>
 * 1 该Class类上的修饰的注解(不包含元注解）
 * 2 该Class父类上标注了@Inherited元注解的注解
 * <p>
 * 当{@link AnnotatedElement}用来表示接口时，isAnnotationPresent获取其修饰注解包括：
 * <p>
 * 1 该interface上的修饰的注解(不包含元注解）
 * 2 也不包含父接口上的注解（无论是否标注@Inherited元注解）
 */
public class IsAnnotationPresentTest {

    /**
     * isAnnotationPresent {@link AnnotatedElement}用来表示Class类时，包含了标注在类上的直接注解
     */
    @Test
    public void isAnnotationPresentOnClassDirectAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.NonAnnotatedClass.class).isAnnotationPresent(Transactional.class)).isFalse();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isTrue();
    }

    /**
     * isAnnotationPresent {@link AnnotatedElement}用来表示Class类时，不包含了标注在类上的注解的元注解
     */
    @Test
    public void isAnnotationPresentOnClassNotIncludedMetaAnnotation() {
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.Transactional.class)).isFalse();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalClass.class).isAnnotationPresent(Component.class)).isFalse();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalClass.class).isAnnotationPresent(Indexed.class)).isFalse();

        assertThat(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.ComposedTransactional.class)).isFalse();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.Transactional.class)).isFalse();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).isAnnotationPresent(Component.class)).isFalse();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).isAnnotationPresent(Indexed.class)).isFalse();
    }

    /**
     * 如果父类上修饰的注解（标注@Inherited元注解），子类会从父类继承标注@Inherited元注解的注解
     * <p>
     * isAnnotationPresent {@link AnnotatedElement}用来表示Class类时，包含了父类标注@Inherited的注解
     */
    @Test
    public void isAnnotationPresentOnClassIncludeSuperClassInheritedAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.SubComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.ComposedTransactional.class)).isFalse();
        assertThat(((AnnotatedElement) AnnotatedModel.SubComposedComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isTrue();
        assertThat(((AnnotatedElement) AnnotatedModel.SubSubComposedComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isTrue();
    }


    /**
     * isAnnotationPresent {@link AnnotatedElement}用来表示Class类时，不包含了实现接口的注解
     */
    @Test
    public void isAnnotationPresentdOnClassNOIncludeInterfaceAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.implementComposedTransactionalInterfaceClass.class).isAnnotationPresent(AnnotatedModel.ComposedTransactional.class)).isFalse();
        assertThat(((AnnotatedElement) AnnotatedModel.implementComposedTransactionalInterfaceClass.class).isAnnotationPresent(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isFalse();
    }

    /**
     * isAnnotationPresent {@link AnnotatedElement}用来表示接口时，包含了接口的注解
     */
    @Test
    public void isAnnotationPresentdOnInterfaceDirectAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalInterface.class).isAnnotationPresent(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isTrue();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalInterface.class).isAnnotationPresent(AnnotatedModel.ComposedTransactional.class)).isTrue();
    }

    /**
     * isAnnotationPresent {@link AnnotatedElement}用来表示接口时，不包含了标注在接口上的注解的元注解
     */
    @Test
    public void isAnnotationPresentOnInterfaceNotIncludedMetaAnnotation() {
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalInterface.class).isAnnotationPresent(AnnotatedModel.Transactional.class)).isFalse();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalInterface.class).isAnnotationPresent(Component.class)).isFalse();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalInterface.class).isAnnotationPresent(Indexed.class)).isFalse();
    }

    /**
     * isAnnotationPresent {@link AnnotatedElement}用来表示接口时，不包含了父接口上的注解
     */
    @Test
    public void isAnnotationPresentOnInterfaceNotIncludeSuperInterfaceAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.SubComposedTransactionalInterface.class).isAnnotationPresent(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isFalse();
        assertThat(((AnnotatedElement) AnnotatedModel.SubComposedTransactionalInterface.class).isAnnotationPresent(AnnotatedModel.ComposedTransactional.class)).isFalse();
    }
}
