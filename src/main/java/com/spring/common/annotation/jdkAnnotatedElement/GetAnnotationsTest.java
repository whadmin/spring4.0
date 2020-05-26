package com.spring.common.annotation.jdkAnnotatedElement;

import com.google.common.collect.Sets;
import com.spring.common.annotation.model.AnnotatedModel;
import org.junit.Test;
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
 * 当{@link AnnotatedElement}用来表示接口时，isAnnotationPresent获取其修饰注解包括：
 * <p>
 * 1 该interface上的修饰的注解(不包含元注解）
 * 2 也不包含父接口上的注解（无论是否标注@Inherited元注解）

 */
public class GetAnnotationsTest {

    private Set<String> names(Class<?>... classes) {
        return stream(classes).map(Class::getName).collect(toSet());
    }

    /**
     * getAnnotations {@link AnnotatedElement}用来表示Class类时 包含了标注在类上的直接注解,不包含其上元注解
     */
    @Test
    public void getAnnotationsOnClassdirectAnnotated() {
        assertThat(Arrays.asList(((AnnotatedElement) AnnotatedModel.NonAnnotatedClass.class).getAnnotations()).isEmpty()).isTrue();

        Set<Class> classname = Arrays.asList(((AnnotatedElement) AnnotatedModel.ComposedTransactionalClass.class).getAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname).isEqualTo(Sets.newHashSet(AnnotatedModel.ComposedTransactional.class));

        Set<Class> classname1 = Arrays.asList(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).getAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname1).isEqualTo(Sets.newHashSet(AnnotatedModel.ComposedComposedTransactionalInherited.class));
    }

    /**
     * getAnnotations {@link AnnotatedElement}用来表示Class类时 包含了父类标注@Inherited的注解
     */
    @Test
    public void getAnnotationsOnClassIncludeSuperClassInheritedAnnotated() {
        Set<Class> classname = Arrays.asList(((AnnotatedElement) AnnotatedModel.SubComposedTransactionalClass.class).getAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname).isEmpty();

        Set<Class> classname1 = Arrays.asList(((AnnotatedElement) AnnotatedModel.SubComposedComposedTransactionalClass.class).getAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname1).isEqualTo(Sets.newHashSet(AnnotatedModel.ComposedComposedTransactionalInherited.class));

        Set<Class> classname2 = Arrays.asList(((AnnotatedElement) AnnotatedModel.SubSubComposedComposedTransactionalClass.class).getAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname2).isEqualTo(Sets.newHashSet(AnnotatedModel.ComposedComposedTransactionalInherited.class));
    }

    /**
     * getAnnotations {@link AnnotatedElement}用来表示接口时，包含了接口的注解,不包含其上元注解
     */
    @Test
    public void getAnnotationsOnInterfaceAnnotated() {
        Set<Class> classname1 = Arrays.asList(((AnnotatedElement) AnnotatedModel.ComposedTransactionalInterface.class).getAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname1).isEqualTo(Sets.newHashSet(AnnotatedModel.ComposedComposedTransactionalInherited.class,AnnotatedModel.ComposedTransactional.class));
    }

    /**
     * getAnnotations {@link AnnotatedElement}用来表示接口时，不包含了父接口上的注解
     */
    @Test
    public void getAnnotationsOnInterfaceNotIncludeSuperInterfaceAnnotated() {
        Set<Class> classname = Arrays.asList(((AnnotatedElement) AnnotatedModel.SubComposedTransactionalInterface.class).getAnnotations()).stream().map(annotatedElement -> annotatedElement.annotationType()).collect(Collectors.toCollection(HashSet::new));
        assertThat(classname).isEmpty();
    }

}
