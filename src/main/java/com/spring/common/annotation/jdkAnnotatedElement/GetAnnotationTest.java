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
 *
 * @Author: wuhao.w
 * @Date: 2020/5/25 12:59
 */
public class GetAnnotationTest {


    /**
     * getAnnotation 包含了标注在类上的直接注解
     */
    @Test
    public void getAnnotationOnClassdirectAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.NonAnnotatedClass.class).getAnnotation(Transactional.class)).isNull();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalClass.class).getAnnotation(AnnotatedModel.ComposedTransactional.class)).isNotNull();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).getAnnotation(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isNotNull();
    }

    /**
     * getAnnotation 不包含了标注在类上的注解的元注解
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
     * getAnnotation 包含了父类标注@Inherited的注解
     */
    @Test
    public void getAnnotationOnClassIncludeSuperClassAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.SubComposedTransactionalClass.class).getAnnotation(AnnotatedModel.ComposedTransactional.class)).isNull();
        assertThat(((AnnotatedElement) AnnotatedModel.SubComposedComposedTransactionalClass.class).getAnnotation(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isNotNull();
        assertThat(((AnnotatedElement) AnnotatedModel.SubSubComposedComposedTransactionalClass.class).getAnnotation(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isNotNull();
    }


    /**
     * isAnnotationPresent 不包含了接口的注解
     */
    @Test
    public void getAnnotationOnClassNOIncludeinterfaceAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.implementComposedTransactionalInterfaceClass.class).getAnnotation(AnnotatedModel.ComposedTransactional.class)).isNull();
        assertThat(((AnnotatedElement) AnnotatedModel.implementComposedTransactionalInterfaceClass.class).getAnnotation(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isNull();
    }
}
