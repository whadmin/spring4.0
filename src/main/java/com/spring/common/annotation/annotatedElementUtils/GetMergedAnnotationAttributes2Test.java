package com.spring.common.annotation.annotatedElementUtils;

import com.spring.common.annotation.model.AnnotatedModel3;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationConfigurationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.core.annotation.AnnotatedElementUtils.getMergedAnnotationAttributes;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/27 16:48
 */
public class GetMergedAnnotationAttributes2Test {

    static <T> T[] asArray(T... arr) {
        return arr;
    }

    @Test
    public void getMergedAnnotationAttributesAliasAnnotation1() {
        AnnotationAttributes attributes = getMergedAnnotationAttributes(AnnotatedModel3.ComposedContextConfigClass.class, AnnotatedModel3.ContextConfig.class.getName());
        assertThat(attributes.getStringArray("locations")).isEqualTo(asArray("composedContextConfig"));
        assertThat(attributes.getStringArray("value")).isEqualTo(asArray("composedContextConfig"));
    }

    @Test
    public void getMergedAnnotationAttributesAliasAnnotation2() {
        AnnotationAttributes attributes = getMergedAnnotationAttributes(AnnotatedModel3.ComposedContextConfig1Class1.class, AnnotatedModel3.ContextConfig.class.getName());
        assertThat(attributes.getStringArray("locations")).isEqualTo(asArray("composedContextConfig"));
        assertThat(attributes.getStringArray("value")).isEqualTo(asArray("composedContextConfig"));

        AnnotationAttributes attributes2 = getMergedAnnotationAttributes(AnnotatedModel3.ComposedContextConfig1Class2.class, AnnotatedModel3.ContextConfig.class.getName());
        assertThat(attributes2.getStringArray("locations")).isEqualTo(asArray("composedContextConfig"));
        assertThat(attributes2.getStringArray("value")).isEqualTo(asArray("composedContextConfig"));
    }

    @Test
    public void getMergedAnnotationAttributesAliasAnnotation3() {
        AnnotationAttributes attributes = getMergedAnnotationAttributes(AnnotatedModel3.ComposedContextConfig2Class1.class, AnnotatedModel3.ContextConfig.class.getName());
        assertThat(attributes.getStringArray("locations")).isEqualTo(asArray("composedContextConfig"));
        assertThat(attributes.getStringArray("value")).isEqualTo(asArray("composedContextConfig"));

        AnnotationAttributes attributes2 = getMergedAnnotationAttributes(AnnotatedModel3.ComposedContextConfig2Class2.class, AnnotatedModel3.ContextConfig.class.getName());
        assertThat(attributes2.getStringArray("locations")).isEqualTo(asArray("composedContextConfig"));
        assertThat(attributes2.getStringArray("value")).isEqualTo(asArray("composedContextConfig"));
    }

    @Test
    public void getMergedAnnotationAttributesAliasAnnotation4() {
        AnnotationAttributes attributes = getMergedAnnotationAttributes(AnnotatedModel3.ComposedContextConfig3Class1.class, AnnotatedModel3.ContextConfig.class.getName());
        assertThat(attributes.getStringArray("locations")).isEqualTo(asArray("composedContextConfig"));
        assertThat(attributes.getStringArray("value")).isEqualTo(asArray("composedContextConfig"));

        AnnotationAttributes attributes2 = getMergedAnnotationAttributes(AnnotatedModel3.ComposedContextConfig3Class2.class, AnnotatedModel3.ContextConfig.class.getName());
        assertThat(attributes2.getStringArray("locations")).isEqualTo(asArray("composedContextConfig"));
        assertThat(attributes2.getStringArray("value")).isEqualTo(asArray("composedContextConfig"));
    }

    @Test
    public void getMergedAnnotationAttributesAliasAnnotation5() {
        assertThatExceptionOfType(AnnotationConfigurationException.class).isThrownBy(() ->
                getMergedAnnotationAttributes(AnnotatedModel3.ComposedContextConfig4Class.class, AnnotatedModel3.ContextConfig.class.getName()));

    }

    @Test
    public void getMergedAnnotationAttributesAliasAnnotation6() {
        AnnotationAttributes attributes = getMergedAnnotationAttributes(AnnotatedModel3.ComposedComposedContextConfigClass1.class, AnnotatedModel3.ContextConfig.class.getName());
        assertThat(attributes.getStringArray("locations")).isEqualTo(asArray("composedContextConfig"));
        assertThat(attributes.getStringArray("value")).isEqualTo(asArray("composedContextConfig"));

        AnnotationAttributes attributes2 = getMergedAnnotationAttributes(AnnotatedModel3.ComposedComposedContextConfigClass2.class, AnnotatedModel3.ContextConfig.class.getName());
        assertThat(attributes2.getStringArray("locations")).isEqualTo(asArray("composedContextConfig"));
        assertThat(attributes2.getStringArray("value")).isEqualTo(asArray("composedContextConfig"));
    }
}
