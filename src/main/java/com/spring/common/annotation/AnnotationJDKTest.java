package com.spring.common.annotation;

import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.AnnotatedElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.core.annotation.AnnotatedElementUtils.isAnnotated;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/22 17:19
 */
public class AnnotationJDKTest {

    @Test
    public void isAnnotationPresentTest(){
        isAnnotationPresentOnNonAnnotatedClass();
        isAnnotationPresentOnClassMetaAnnotation();
    }


    @Test
    public void isAnnotationPresentOnNonAnnotatedClass() {
        assertThat(((AnnotatedElement)AnnotatedModel.NonAnnotatedClass.class).isAnnotationPresent(Transactional.class)).isFalse();
    }


    @Test
    public void isAnnotationPresentOnClassMetaAnnotation() {
        assertThat(((AnnotatedElement)AnnotatedModel.ComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(((AnnotatedElement)AnnotatedModel.ComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.Transactional.class)).isFalse();
        assertThat(((AnnotatedElement)AnnotatedModel.ComposedTransactionalClass.class).isAnnotationPresent(Component.class)).isFalse();
        assertThat(((AnnotatedElement)AnnotatedModel.ComposedTransactionalClass.class).isAnnotationPresent(Indexed.class)).isFalse();
    }

    @Test
    public void isAnnotatedOnClassWithMetaDepth() {
        assertThat(((AnnotatedElement)AnnotatedModel.ComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.ComposedTransactional.class)).isFalse();
        assertThat(isAnnotated(AnnotatedModel.SubTransactionalComponentClass.class, AnnotatedModel.Transactional.class.getName())).as("isAnnotated() does not search the class hierarchy.").isFalse();
        assertThat(isAnnotated(AnnotatedModel.SubTransactionalComponentClass.class, Component.class.getName())).as("isAnnotated() does not search the class hierarchy.").isFalse();
        assertThat(isAnnotated(AnnotatedModel.SubTransactionalComponentClass.class, Indexed.class.getName())).as("isAnnotated() does not search the class hierarchy.").isFalse();
    }
}
