package com.spring.common.annotation.jdkAnnotatedElement;

import com.google.common.collect.Sets;
import com.spring.common.annotation.model.AnnotatedModel;
import org.junit.Test;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * getDeclaredAnnotations 用来获取{@link AnnotatedElement}上修饰的注解
 * <p>
 * 当{@link AnnotatedElement}用来表示Class类时包括：
 * <p>
 * 1 该Class类上的修饰的注解
 * 2 不包含标注在类上的注解的元注解
 * 3 不包含从父类继承父类上修饰的注解（标注@Inherited元注解）
 *
 * @Author: wuhao.w
 * @Date: 2020/5/25 12:59
 */
public class GetDeclaredAnnotationsTest {

    /**
     * getAnnotation 包含了标注在类上的直接注解,但不包含其上元注解
     */
    @Test
    public void getDeclaredAnnotationsOnClassdirectAnnotated() {
        assertThat(Arrays.asList(((AnnotatedElement) AnnotatedModel.NonAnnotatedClass.class).getDeclaredAnnotations()).isEmpty()).isTrue();

        Set<Class> classname = Arrays.asList(((AnnotatedElement) AnnotatedModel.ComposedTransactionalClass.class).getDeclaredAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname).isEqualTo(Sets.newHashSet(AnnotatedModel.ComposedTransactional.class));

        Set<Class> classname1 = Arrays.asList(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).getDeclaredAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname1).isEqualTo(Sets.newHashSet(AnnotatedModel.ComposedComposedTransactionalInherited.class));
    }

    /**
     * 不包含从父类继承父类上修饰的注解（标注@Inherited元注解）
     */
    @Test
    public void getDeclaredAnnotationsIncludeSuperClassAnnotated() {
        Set<Class> classname = Arrays.asList(((AnnotatedElement) AnnotatedModel.SubComposedTransactionalClass.class).getDeclaredAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname).isEmpty();

        Set<Class> classname1 = Arrays.asList(((AnnotatedElement) AnnotatedModel.SubComposedComposedTransactionalClass.class).getDeclaredAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname1).isEmpty();

        Set<Class> classname2 = Arrays.asList(((AnnotatedElement) AnnotatedModel.SubSubComposedComposedTransactionalClass.class).getDeclaredAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname2).isEmpty();
    }
}
