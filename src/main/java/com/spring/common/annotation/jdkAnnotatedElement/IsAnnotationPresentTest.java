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
 *
 * @Author: wuhao.w
 * @Date: 2020/5/22 17:19
 */
public class IsAnnotationPresentTest {


    /**
     * isAnnotationPresent 包含了标注在类上的直接注解
     */
    @Test
    public void isAnnotationPresentOnClassdirectAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.NonAnnotatedClass.class).isAnnotationPresent(Transactional.class)).isFalse();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.ComposedTransactional.class)).isTrue();
        assertThat(((AnnotatedElement) AnnotatedModel.ComposedComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isTrue();
    }


    /**
     * isAnnotationPresent 不包含了标注在类上的注解的元注解
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
     * isAnnotationPresent 包含了父类标注@Inherited的注解
     */
    @Test
    public void isAnnotatedOnClassIncludeSuperClassAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.SubComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.ComposedTransactional.class)).isFalse();
        assertThat(((AnnotatedElement) AnnotatedModel.SubComposedComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isTrue();
        assertThat(((AnnotatedElement) AnnotatedModel.SubSubComposedComposedTransactionalClass.class).isAnnotationPresent(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isTrue();
    }


    /**
     * isAnnotationPresent 不包含了接口的注解
     */
    @Test
    public void isAnnotatedOnClassNOIncludeinterfaceAnnotated() {
        assertThat(((AnnotatedElement) AnnotatedModel.implementComposedTransactionalInterfaceClass.class).isAnnotationPresent(AnnotatedModel.ComposedTransactional.class)).isFalse();
        assertThat(((AnnotatedElement) AnnotatedModel.implementComposedTransactionalInterfaceClass.class).isAnnotationPresent(AnnotatedModel.ComposedComposedTransactionalInherited.class)).isFalse();
    }

}
