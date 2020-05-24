package com.spring.common.annotation.annotatedElementUtils;

import com.spring.common.annotation.model.AnnotatedModel;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.core.annotation.AnnotatedElementUtils.getMetaAnnotationTypes;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/24 15:30
 */
public class GetMetaAnnotationTypeTest {

    /**
     * 从指定类，指定注解上获取
     * 获取所有自定义元注解类型的全限定类名
     */
    @Test
    public void getMetaAnnotationTypeTest(){
        //NonAnnotatedClass类没有被注解修饰，更不存在元注解修饰
        getMetaAnnotationTypesOnNonAnnotatedClass();
        //获取TransactionalComponentClass类所有元注解类型的全限定类名
        getMetaAnnotationTypesOnClassWithMetaDepth1();
        //获取ComposedTransactionalComponentClass类所有元注解类型的全限定类名
        getMetaAnnotationTypesOnClassWithMetaDepth2();
    }

    @Test
    public void getMetaAnnotationTypesOnNonAnnotatedClass() {
        assertThat(getMetaAnnotationTypes(AnnotatedModel.NonAnnotatedClass.class, AnnotatedModel.ComposedTransactional.class).isEmpty()).isTrue();
        assertThat(getMetaAnnotationTypes(AnnotatedModel.NonAnnotatedClass.class, AnnotatedModel.ComposedTransactional.class.getName()).isEmpty()).isTrue();
    }

    @Test
    public void getMetaAnnotationTypesOnClassWithMetaDepth1() {
        Set<String> names = getMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class);
        assertThat(names).isEqualTo(names(AnnotatedModel.Transactional.class, Component.class, Indexed.class));

        names = getMetaAnnotationTypes(AnnotatedModel.ComposedTransactionalClass.class, AnnotatedModel.ComposedTransactional.class.getName());
        assertThat(names).isEqualTo(names(AnnotatedModel.Transactional.class, Component.class, Indexed.class));
    }

    @Test
    public void getMetaAnnotationTypesOnClassWithMetaDepth2() {
        Set<String> names = getMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedComposedTransactional.class);
        assertThat(names).isEqualTo(names(AnnotatedModel.ComposedTransactional.class, AnnotatedModel.Transactional.class, Component.class, Indexed.class));

        names = getMetaAnnotationTypes(AnnotatedModel.ComposedComposedTransactionalClass.class, AnnotatedModel.ComposedComposedTransactional.class.getName());
        assertThat(names).isEqualTo(names(AnnotatedModel.ComposedTransactional.class, AnnotatedModel.Transactional.class, Component.class, Indexed.class));
    }

    private Set<String> names(Class<?>... classes) {
        return stream(classes).map(Class::getName).collect(toSet());
    }
}
