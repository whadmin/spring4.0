package com.spring.ioc.core.common.classmetadata;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.*;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class MetadataTests {

    @Component("myName")
    private static class AnnotatedComponent implements Serializable {


        public interface TestInterface  {
        }

        protected static class StaticInner {
        }

        private class Inner {

        }
    }

    @Test
    public void doClassMetadataInfo() throws Exception {
        ClassMetadata metadata = new StandardClassMetadata(AnnotatedComponent.class);
        doClassMetadataInfo(metadata);
    }

    private void doClassMetadataInfo(ClassMetadata metadata) {
        assertThat(metadata.getClassName(), is(AnnotatedComponent.class.getName()));
        assertThat(metadata.isInterface(), is(false));
        assertThat(metadata.isAnnotation(), is(false));
        assertThat(metadata.isAbstract(), is(false));
        assertThat(metadata.isConcrete(), is(true));
        assertThat(metadata.hasSuperClass(), is(true));
        assertThat(metadata.getEnclosingClassName().equals("com.spring.ioc.core.common.classmetadata.MetadataTests"), is(true));
        assertThat(metadata.isIndependent(), is(true));
        assertThat(metadata.hasEnclosingClass(), is(true));
        assertThat(metadata.hasEnclosingClass(), is(true));
        assertThat(metadata.getSuperClassName(), is(Object.class.getName()));
        assertThat(metadata.getInterfaceNames().length, is(1));
        assertThat(metadata.getInterfaceNames()[0], is(Serializable.class.getName()));
        metadata.getMemberClassNames();
    }


    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DirectAnnotation {

        @AliasFor("myValue")
        String value() default "";

        @AliasFor("value")
        String myValue() default "";

        String additional() default "direct";
    }
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface IsAnnotatedAnnotation {
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @DirectAnnotation("meta")
    @IsAnnotatedAnnotation
    public @interface MetaAnnotation {

        String additional() default "meta";
    }

    private static class AnnotatedComponent3 implements Serializable {
        @DirectAnnotation("direct")
        @MetaAnnotation
        public void meta() {
        }
    }
    @Test
    public void doAnnotationMetadataInfo3() throws Exception {
        AnnotationMetadata metadata = new StandardAnnotationMetadata(AnnotatedComponent3.class);
        Set<MethodMetadata> methods = metadata.getAnnotatedMethods(DirectAnnotation.class.getName());
        MethodMetadata method = methods.iterator().next();

        assertEquals("direct", method.getAnnotationAttributes(DirectAnnotation.class.getName()).get("value"));
        assertEquals("direct", method.getAnnotationAttributes(DirectAnnotation.class.getName()).get("myValue"));

        List<Object> allMeta = method.getAllAnnotationAttributes(DirectAnnotation.class.getName()).get("value");
        assertThat(new HashSet<>(allMeta), is(equalTo(new HashSet<Object>(Arrays.asList("direct", "meta")))));

        allMeta = method.getAllAnnotationAttributes(DirectAnnotation.class.getName()).get("additional");
        assertThat(new HashSet<>(allMeta), is(equalTo(new HashSet<Object>(Arrays.asList("direct")))));
    }
}
