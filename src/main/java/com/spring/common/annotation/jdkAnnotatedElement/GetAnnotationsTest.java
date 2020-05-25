package com.spring.common.annotation.jdkAnnotatedElement;

import com.google.common.collect.Sets;
import com.spring.common.annotation.model.AnnotatedModel;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * getAnnotations 用来获取{@link AnnotatedElement}上修饰的所有注解
 * <p>
 * 当{@link AnnotatedElement}用来表示Class类时包括：
 * <p>
 * 1 该Class类上的修饰的注解
 * 2 不包含标注在类上的注解的元注解
 * 3 包含从父类继承父类上修饰的注解（标注@Inherited元注解）
 *
 * @Author: wuhao.w
 * @Date: 2020/5/25 12:59
 */
public class GetAnnotationsTest {

    private Set<String> names(Class<?>... classes) {
        return stream(classes).map(Class::getName).collect(toSet());
    }


    /**
     * getAnnotation 包含了标注在类上的直接注解,不包含其上元注解
     */
    @Test
    public void getAnnotationsOnClassdirectAnnotated() {
        assertThat(Arrays.asList(((AnnotatedElement) AnnotatedModel.NonAnnotatedClass.class).getAnnotations()).isEmpty()).isTrue();

        Set<Class> classname = Arrays.asList(((AnnotatedElement) AnnotatedModel.ComposedTransactionalClass.class).getAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname).isEqualTo(Sets.newHashSet(AnnotatedModel.ComposedTransactional.class));

        Set<Class> classname1 = Arrays.asList(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).getAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname1).isEqualTo(Sets.newHashSet(AnnotatedModel.ComposedComposedTransactionalInherited.class));
    }

    @Test
    public void getAnnotationOnClassIncludeSuperClassAnnotated() {
        Set<Class> classname = Arrays.asList(((AnnotatedElement) AnnotatedModel.SubComposedTransactionalClass.class).getAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname).isEmpty();

        Set<Class> classname1 = Arrays.asList(((AnnotatedElement) AnnotatedModel.SubComposedComposedTransactionalClass.class).getAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname1).isEqualTo(Sets.newHashSet(AnnotatedModel.ComposedComposedTransactionalInherited.class));

        Set<Class> classname2 = Arrays.asList(((AnnotatedElement) AnnotatedModel.SubSubComposedComposedTransactionalClass.class).getAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname2).isEqualTo(Sets.newHashSet(AnnotatedModel.ComposedComposedTransactionalInherited.class));
    }

}
