package com.spring.common.annotation.annotatedElementUtils;

import com.spring.common.annotation.model.AnnotatedModel;
import com.spring.common.annotation.model.AnnotatedModel2;
import org.junit.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.AnnotatedElement;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.core.annotation.AnnotatedElementUtils.getAllAnnotationAttributes;
import static org.springframework.core.annotation.AnnotatedElementUtils.getMetaAnnotationTypes;

/**
 * getAllAnnotationAttributes：用来获取{@link AnnotatedElement}装饰注解的属性
 *
 * 要知道装饰注解的属性，首先需要知道hasAnnotation能够获取该{@link AnnotatedElement}注解的范围
 *
 * 当{@link AnnotatedElement}用来表示Class类时，获取该{@link AnnotatedElement}注解的范围和{@link AnnotatedElementUtils#isAnnotated}一致包括：
 *
 *   1  该Class类上的修饰的注解
 *   2  该Class类上的修饰的注解同时也包括元注解
 *   3  该Class父类上标注了@Inherited元注解的注解
 *   4  该Class父类上标注了@Inherited元注解的注解的元注解
 *
 *  同时被指定注解可能存在多次装饰该注解类，这时一个属性可能存在多个值的情况：
 *
 *  在指定类中直接注解和元注解属性，元注解和元注解合并
 *
 *  如果存在父类继承的情况
     *   如下情况合并：
     *     指定类的直接注解和父类元注解相同属性合并
     *     指定类的元注解和父类元注解相同属性合并
     *     指定类的元注解和父类注解相同属性合并
     *   如下情况覆盖：
     *     指定类的注解和父类注解相同属性覆盖
 *
 *
 */
public class GetAllAnnotationAttributesTest {

    private static final String TX_NAME = AnnotatedModel2.Transactional.class.getName();

    private Set<String> names(Class<?>... classes) {
        return stream(classes).map(Class::getName).collect(toSet());
    }


    @Test
    public void getAllAnnotationAttributesOnNonAnnotatedClass() {
        assertThat(getMetaAnnotationTypes(AnnotatedModel.NonAnnotatedClass.class, AnnotatedModel2.Transactional.class).isEmpty()).isTrue();
    }

    @Test
    public void getAllAnnotationAttributesOnAnnotatedClass() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.TransactionalClass.class,TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("value")).isEqualTo(asList("default"));

        MultiValueMap<String, Object> attributes2 = getAllAnnotationAttributes(AnnotatedModel2.ComposedTransactionalClass1.class,TX_NAME);
        assertThat(attributes2).isNotNull();
        assertThat(attributes2.get("value")).isEqualTo(asList("composedTransactional1"));

        MultiValueMap<String, Object> attributes3 = getAllAnnotationAttributes(AnnotatedModel2.ComposedTransactionalClass2.class,TX_NAME);
        assertThat(attributes3).isNotNull();
        assertThat(attributes3.get("value")).isEqualTo(asList("composedTransactional2"));
    }

    /**
     * 同时被指定注解可能存在多次装饰该注解类，这时一个属性可能存在多个值的情况,合并情况
     */
    @Test
    public void getAllAnnotationAttributesOnMultipleAnnotatedClass() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.MultipleAnnotationClass1.class,TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("value")).isEqualTo(asList("composedTransactional1","composedTransactional2"));

        MultiValueMap<String, Object> attributes2 = getAllAnnotationAttributes(AnnotatedModel2.MultipleAnnotationClass2.class,TX_NAME);
        assertThat(attributes2).isNotNull();
        assertThat(attributes2.get("value")).isEqualTo(asList("default","composedTransactional1"));

        MultiValueMap<String, Object> attributes3 = getAllAnnotationAttributes(AnnotatedModel2.MultipleAnnotationClass3.class,TX_NAME);
        assertThat(attributes3).isNotNull();
        assertThat(attributes3.get("value")).isEqualTo(asList("default","composedTransactional2"));
    }

    @Test
    public void getAllAnnotationAttributesOnMultipleAnnotatedIncludeClassAndSuperClass() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.SubTransactionalClass1.class,TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("value")).isEqualTo(asList("composedTransactional1","default"));

        MultiValueMap<String, Object> attributes2 = getAllAnnotationAttributes(AnnotatedModel2.SubTransactionalClass2.class,TX_NAME);
        assertThat(attributes2).isNotNull();
        assertThat(attributes2.get("value")).isEqualTo(asList("composedTransactional2","default"));

        MultiValueMap<String, Object> attributes3 = getAllAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalClass1.class,TX_NAME);
        assertThat(attributes3).isNotNull();
        assertThat(attributes3.get("value")).isEqualTo(asList("sub","composedTransactional1"));

        MultiValueMap<String, Object> attributes4 = getAllAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalClass2.class,TX_NAME);
        assertThat(attributes4).isNotNull();
        assertThat(attributes4.get("value")).isEqualTo(asList("sub","composedTransactional2"));

        MultiValueMap<String, Object> attributes5 = getAllAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalClass3.class,TX_NAME);
        assertThat(attributes5).isNotNull();
        assertThat(attributes5.get("value")).isEqualTo(asList("composedTransactional2","composedTransactional1"));

        MultiValueMap<String, Object> attributes6 = getAllAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalClass4.class,TX_NAME);
        assertThat(attributes6).isNotNull();
        assertThat(attributes6.get("value")).isEqualTo(asList("composedTransactional1","composedTransactional2"));
    }

    @Test
    public void getAllAnnotationAttributesOnMultipleAnnotatedIncludeClassAndSuperClass2() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.SubTransactionalClass3.class,TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("value")).isEqualTo(asList("sub"));
    }


    @Test
    public void getAllAnnotationAttributesOnMultipleAnnotatedIncludeClassAndSuperClass3() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalInheritedClass1.class,TX_NAME);
        assertThat(attributes).isNull();

        MultiValueMap<String, Object> attributes2 = getAllAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalInheritedClass2.class,TX_NAME);
        assertThat(attributes2).isNull();
    }
}
