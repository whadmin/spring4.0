package com.spring.common.annotation.jdkAnnotatedElement;

import com.spring.common.annotation.model.AnnotatedModel;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.AnnotatedElement;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * getAnnotation 用来获取{@link AnnotatedElement}上修饰的指定注解
 * <p>
 * 当{@link AnnotatedElement}用来表示Class类时包括：
 * <p>
 * 1 该Class类上的修饰的注解
 * 2 不包含标注在类上的注解的元注解
 * 3 包含从父类继承父类上修饰的注解（标注@Inherited元注解）
 * 当{@link AnnotatedElement}用来表示接口时，isAnnotationPresent获取其修饰注解包括：
 * <p>
 * 1 该interface上的修饰的注解(不包含元注解）
 * 2 也不包含父接口上的注解（无论是否标注@Inherited元注解）

 */
public class GetAnnotationTest {


    /**
     * getAnnotation {@link AnnotatedElement}用来表示Class类时，包含了标注在类上的直接注解
     */
    @Test
    public void getAnnotationOnClassDirectAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.NonAnnotatedClass.class).getAnnotation(Transactional.class)).isNull();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalClass.class).getAnnotation(AnnotatedModel.ComposedTransactional.class)).isNotNull();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).getAnnotation(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isNotNull();
    }

    /**
     * getAnnotation {@link AnnotatedElement}用来表示Class类时，包含了标注在类上的注解的元注解
     */
    @Test
    public void getAnnotationOnClassNotIncludedMetaAnnotation() {
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalClass.class).getAnnotation(AnnotatedModel.Transactional.class)).isNull();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalClass.class).getAnnotation(Component.class)).isNull();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalClass.class).getAnnotation(Indexed.class)).isNull();

        assertThat(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).getAnnotation(AnnotatedModel.ComposedTransactional.class)).isNull();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).getAnnotation(AnnotatedModel.Transactional.class)).isNull();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).getAnnotation(Component.class)).isNull();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).getAnnotation(Indexed.class)).isNull();
    }

    /**
     * 如果父类注解标注@Inherited，子类会从父类基础该注解
     * <p>
     * getAnnotation {@link AnnotatedElement}用来表示Class类时，包含了父类标注@Inherited的注解
     */
    @Test
    public void getAnnotationOnClassIncludeSuperClassInheritedAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.SubComposedTransactionalClass.class).getAnnotation(AnnotatedModel.ComposedTransactional.class)).isNull();
        assertThat(((AnnotatedElement) AnnotatedModel.SubComposedComposedTransactionalClass.class).getAnnotation(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isNotNull();
        assertThat(((AnnotatedElement) AnnotatedModel.SubSubComposedComposedTransactionalClass.class).getAnnotation(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isNotNull();
    }


    /**
     * isAnnotationPresent {@link AnnotatedElement}用来表示Class类时，不包含了实现接口的注解
     */
    @Test
    public void getAnnotationOnClassNOIncludeinterfaceAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.implementComposedTransactionalInterfaceClass.class).getAnnotation(AnnotatedModel.ComposedTransactional.class)).isNull();
        assertThat(((AnnotatedElement) AnnotatedModel.implementComposedTransactionalInterfaceClass.class).getAnnotation(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isNull();
    }

    /**
     * isAnnotationPresent {@link AnnotatedElement}用来表示接口时，包含了接口的注解
     */
    @Test
    public void getAnnotationPresentdOnInterfaceDirectAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalInterface.class).getAnnotation(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isNotNull();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalInterface.class).getAnnotation(AnnotatedModel.ComposedTransactional.class)).isNotNull();
    }

    /**
     * isAnnotationPresent {@link AnnotatedElement}用来表示接口时，不包含了标注在接口上的注解的元注解
     */
    @Test
    public void getAnnotationPresentOnInterfaceNotIncludedMetaAnnotation() {
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalInterface.class).getAnnotation(AnnotatedModel.Transactional.class)).isNull();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalInterface.class).getAnnotation(Component.class)).isNull();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalInterface.class).getAnnotation(Indexed.class)).isNull();
    }

    /**
     * isAnnotationPresent {@link AnnotatedElement}用来表示接口时，不包含了父接口上的注解
     */
    @Test
    public void getAnnotationPresentOnInterfaceNotIncludeSuperInterfaceAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.SubComposedTransactionalInterface.class).getAnnotation(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isNull();
        assertThat(((AnnotatedElement) AnnotatedModel.SubComposedTransactionalInterface.class).getAnnotation(AnnotatedModel.ComposedTransactional.class)).isNull();
    }
}
