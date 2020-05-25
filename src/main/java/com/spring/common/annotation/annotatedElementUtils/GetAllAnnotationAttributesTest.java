package com.spring.common.annotation.annotatedElementUtils;

import com.spring.common.annotation.model.AnnotatedModel;
import com.spring.common.annotation.model.AnnotatedModel2;
import org.junit.Test;
import org.springframework.stereotype.Component;
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
 * getAllAnnotationAttributes：用来获取装饰{@link AnnotatedElement}指定注解的所有属性

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
