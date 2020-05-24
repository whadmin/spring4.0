package com.spring.common.annotation.annotatedElementUtils;

import com.spring.common.annotation.model.AnnotatedModel;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.core.annotation.AnnotatedElementUtils.isAnnotated;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/24 15:34
 */
public class isAnnotatedTest {

    /**
     * 判断指定的{@link AnnotatedElement}是否被指定注解修饰（也包括注解上的元注解）
     *
     * 如果{@link AnnotatedElement}类型为Class,修饰注解包括Class上的修饰的注解(及其元注解)，不包括其祖先类Class上的修饰的注解(及其元注解)
     * 如果{@link AnnotatedElement}类型为Annotation，修饰注解包括为其上的元注解
     *
     */
    @Test
    public void isAnnotatedTest(){
        //NonAnnotatedClass类没有被注解修饰，因此不被Transactional注解修饰
        isAnnotatedOnNonAnnotatedClass();
        //isAnnotated 判断Class是否被指定注解修饰包含了Class上的修饰的注解(及其元注解)
        isAnnotatedOnClassWithMetaDepth();
        //isAnnotated 判断Annotation是否被指定注解修饰包含了其祖先类Class上的修饰的注解(及其元注解)
        isAnnotatedOnClassWithMetaDepth();
        //isAnnotated 判断Class是否被指定注解修饰包含了其上的元注解
        //isAnnotatedForPlainTypes();
    }

    @Test
    public void isAnnotatedOnNonAnnotatedClass() {
        assertThat(isAnnotated(AnnotatedModel.NonAnnotatedClass.class, AnnotatedModel.Transactional.class)).isFalse();
        assertThat(isAnnotated(AnnotatedModel.NonAnnotatedClass.class, AnnotatedModel.Transactional.class.getName())).isFalse();
    }

    @Test
    public void isAnnotatedOnClassMetaAnnotation() {
        assertThat(isAnnotated(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class.getName())).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.Transactional.class.getName())).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedTransactionalClass.class, Component.class.getName())).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedTransactionalClass.class, Indexed.class.getName())).isTrue();

        assertThat(isAnnotated(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedTransactionalClass.class, Component.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedTransactionalClass.class, Indexed.class)).isTrue();


        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedComposedTransactional.class.getName())).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class.getName())).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.Transactional.class.getName())).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, Component.class.getName())).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, Indexed.class.getName())).isTrue();

        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedComposedTransactional.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.Transactional.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, Component.class)).isTrue();
        assertThat(isAnnotated(AnnotatedModel.ComposedComposedTransactionalClass.class, Indexed.class)).isTrue();
    }

    @Test
    public void isAnnotatedOnClassWithMetaDepth() {
        assertThat(isAnnotated(AnnotatedModel.SubTransactionalComponentClass.class, AnnotatedModel.ComposedTransactional.class.getName())).as("isAnnotated() does not search the class hierarchy.").isFalse();
        assertThat(isAnnotated(AnnotatedModel.SubTransactionalComponentClass.class, AnnotatedModel.Transactional.class.getName())).as("isAnnotated() does not search the class hierarchy.").isFalse();
        assertThat(isAnnotated(AnnotatedModel.SubTransactionalComponentClass.class, Component.class.getName())).as("isAnnotated() does not search the class hierarchy.").isFalse();
        assertThat(isAnnotated(AnnotatedModel.SubTransactionalComponentClass.class, Indexed.class.getName())).as("isAnnotated() does not search the class hierarchy.").isFalse();

        assertThat(isAnnotated(AnnotatedModel.SubTransactionalComponentClass.class, AnnotatedModel.ComposedTransactional.class)).as("isAnnotated() does not search the class hierarchy.").isFalse();
        assertThat(isAnnotated(AnnotatedModel.SubTransactionalComponentClass.class, AnnotatedModel.Transactional.class)).as("isAnnotated() does not search the class hierarchy.").isFalse();
        assertThat(isAnnotated(AnnotatedModel.SubTransactionalComponentClass.class, Component.class)).as("isAnnotated() does not search the class hierarchy.").isFalse();
        assertThat(isAnnotated(AnnotatedModel.SubTransactionalComponentClass.class, Indexed.class)).as("isAnnotated() does not search the class hierarchy.").isFalse();
    }
}
