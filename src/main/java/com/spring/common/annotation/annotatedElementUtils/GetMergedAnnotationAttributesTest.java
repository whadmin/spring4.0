package com.spring.common.annotation.annotatedElementUtils;

import com.spring.common.annotation.model.AnnotatedModel;
import com.spring.common.annotation.model.AnnotatedModel2;
import com.spring.common.annotation.model.AnnotatedModel3;
import org.apache.catalina.startup.ContextConfig;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationAttributes;
import java.util.Set;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.core.annotation.AnnotatedElementUtils.*;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/25 20:08
 */
public class GetMergedAnnotationAttributesTest {

    private static final String TX_NAME = AnnotatedModel2.Transactional.class.getName();

    private Set<String> names(Class<?>... classes) {
        return stream(classes).map(Class::getName).collect(toSet());
    }

    @Test
    public void getMergedAnnotationAttributesOnNonAnnotatedClass() {
        assertThat(getMergedAnnotationAttributes(AnnotatedModel.NonAnnotatedClass.class, AnnotatedModel2.Transactional.class)).isNull();
    }

    @Test
    public void getMergedAnnotationAttributesOnAnnotatedClass() {
        AnnotationAttributes attributes = getMergedAnnotationAttributes(AnnotatedModel2.TransactionalClass.class,TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("value")).isEqualTo("default");

        AnnotationAttributes attributes2 = getMergedAnnotationAttributes(AnnotatedModel2.ComposedTransactionalClass1.class,TX_NAME);
        assertThat(attributes2).isNotNull();
        assertThat(attributes2.get("value")).isEqualTo("composedTransactional1");

        AnnotationAttributes attributes3 = getMergedAnnotationAttributes(AnnotatedModel2.ComposedTransactionalClass2.class,TX_NAME);
        assertThat(attributes3).isNotNull();
        assertThat(attributes3.get("value")).isEqualTo("composedTransactional2");
    }

    /**
     * 同时被指定注解可能存在多次装饰该注解类，这时一个属性可能存在多个值的情况,合并情况
     */
    @Test
    public void getAllAnnotationAttributesOnMultipleAnnotatedClass() {
        AnnotationAttributes attributes = getMergedAnnotationAttributes(AnnotatedModel2.MultipleAnnotationClass1.class,TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("value")).isEqualTo("composedTransactional1");

        AnnotationAttributes attributes2 = getMergedAnnotationAttributes(AnnotatedModel2.MultipleAnnotationClass2.class,TX_NAME);
        assertThat(attributes2).isNotNull();
        assertThat(attributes2.get("value")).isEqualTo("default");

        AnnotationAttributes attributes3 = getMergedAnnotationAttributes(AnnotatedModel2.MultipleAnnotationClass3.class,TX_NAME);
        assertThat(attributes3).isNotNull();
        assertThat(attributes3.get("value")).isEqualTo("default");
    }

    @Test
    public void getAllAnnotationAttributesOnMultipleAnnotatedIncludeClassAndSuperClass() {
        AnnotationAttributes attributes = getMergedAnnotationAttributes(AnnotatedModel2.SubTransactionalClass1.class,TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("value")).isEqualTo("composedTransactional1");

        AnnotationAttributes attributes2 = getMergedAnnotationAttributes(AnnotatedModel2.SubTransactionalClass2.class,TX_NAME);
        assertThat(attributes2).isNotNull();
        assertThat(attributes2.get("value")).isEqualTo("composedTransactional2");

        AnnotationAttributes attributes3 = getMergedAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalClass1.class,TX_NAME);
        assertThat(attributes3).isNotNull();
        assertThat(attributes3.get("value")).isEqualTo("sub");

        AnnotationAttributes attributes4 = getMergedAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalClass2.class,TX_NAME);
        assertThat(attributes4).isNotNull();
        assertThat(attributes4.get("value")).isEqualTo("sub");

        AnnotationAttributes attributes5 = getMergedAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalClass3.class,TX_NAME);
        assertThat(attributes5).isNotNull();
        assertThat(attributes5.get("value")).isEqualTo("composedTransactional2");

        AnnotationAttributes attributes6 = getMergedAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalClass4.class,TX_NAME);
        assertThat(attributes6).isNotNull();
        assertThat(attributes6.get("value")).isEqualTo("composedTransactional1");
    }

    @Test
    public void getAllAnnotationAttributesOnMultipleAnnotatedIncludeClassAndSuperClass2() {
        AnnotationAttributes attributes = getMergedAnnotationAttributes(AnnotatedModel2.SubTransactionalClass3.class,TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("value")).isEqualTo("sub");
    }

    @Test
    public void getAllAnnotationAttributesOnMultipleAnnotatedIncludeClassAndSuperClass3() {
        AnnotationAttributes attributes = getMergedAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalInheritedClass1.class,TX_NAME);
        assertThat(attributes).isNull();
        AnnotationAttributes attributes2 = getMergedAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalInheritedClass2.class,TX_NAME);
        assertThat(attributes2).isNotNull();
        assertThat(attributes2.get("value")).isEqualTo("composedTransactionalInherited");
    }


    @Test
    public void getMergedAnnotationAttributesWithConventionBasedComposedAnnotation() {
        AnnotationAttributes attributes = getMergedAnnotationAttributes(AnnotatedModel3.ConventionBasedComposedContextConfigClass.class, AnnotatedModel3.ContextConfig.class.getName());
        assertThat(attributes.getStringArray("locations")).isEqualTo(asArray("explicitDeclaration"));
        assertThat(attributes.getStringArray("value")).isEqualTo(asArray("explicitDeclaration"));
    }

    static <T> T[] asArray(T... arr) {
        return arr;
    }
}
