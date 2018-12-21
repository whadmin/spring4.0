package com.spring.common.classmetadata;

import org.junit.Test;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

public class AnnotationMetadataTests1 {

    public enum SubclassEnum {
        FOO,
        BAR
    }

    public static enum SomeEnum {
        LABEL1, LABEL2, DEFAULT
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface EnumSubclasses {

        SubclassEnum[] value();
    }

    @Target({})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NestedAnno {

        String value() default "default";

        SomeEnum anEnum() default SomeEnum.DEFAULT;

        Class<?>[] classArray() default Void.class;
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SpecialAttr {

        Class<?> clazz();

        Thread.State state();

        NestedAnno nestedAnno();

        NestedAnno[] nestedAnnoArray();

        NestedAnno optional() default @NestedAnno(value = "optional", anEnum = SomeEnum.DEFAULT, classArray = Void.class);

        NestedAnno[] optionalArray() default { @NestedAnno(value = "optional", anEnum = SomeEnum.DEFAULT, classArray = Void.class) };
    }

    @Component("myName")
    @Scope("myScope")
    @SpecialAttr(clazz = String.class, state = Thread.State.NEW,
            nestedAnno = @NestedAnno(value = "na", anEnum = SomeEnum.LABEL1, classArray = {String.class}),
            nestedAnnoArray = {@NestedAnno, @NestedAnno(value = "na1", anEnum = SomeEnum.LABEL2, classArray = {Number.class})})
    @SuppressWarnings({"serial", "unused"})
    @MetadataTests.DirectAnnotation("direct")
    @MetadataTests.MetaAnnotation
    @EnumSubclasses({SubclassEnum.FOO, SubclassEnum.BAR})
    private static class AnnotatedComponent1 implements Serializable {
    }


    @Test
    public void doAnnotationMetadataInfo1() throws Exception {
        AnnotationMetadata metadata = new StandardAnnotationMetadata(AnnotatedComponent1.class, true);

        assertThat(metadata.hasAnnotation(EnumSubclasses.class.getName()), is(true));
        assertThat(metadata.getAnnotationTypes().size(), is(6));
        assertThat(metadata.getAnnotationTypes().contains(EnumSubclasses.class.getName()), is(true));
        assertThat(metadata.getAnnotationTypes().contains(Component.class.getName()), is(true));

        AnnotationAttributes compAttrs = (AnnotationAttributes) metadata.getAnnotationAttributes(Component.class.getName());
        assertThat(compAttrs.size(), is(1));
        assertThat(compAttrs.getString("value"), is("myName"));

        List<Object> allMeta=null;
        AnnotationAttributes specialAttrs=null;
         specialAttrs = (AnnotationAttributes) metadata.getAnnotationAttributes(SpecialAttr.class.getName());
        assertThat(specialAttrs.size(), is(6));
        assertTrue(String.class.isAssignableFrom(specialAttrs.getClass("clazz")));
        assertTrue(specialAttrs.getClass("clazz").equals(String.class));

        specialAttrs = (AnnotationAttributes) metadata.getAnnotationAttributes(
                SpecialAttr.class.getName(), true);
        assertThat(specialAttrs.size(), is(6));
        assertThat(specialAttrs.get("clazz"), is((Object) String.class.getName()));
        assertThat(specialAttrs.getClass("clazz"), is(String.class.getName()));

        AnnotationAttributes nestedAnno = specialAttrs.getAnnotation("nestedAnno");
        assertThat("na", is(nestedAnno.getString("value")));
        assertTrue(nestedAnno.getEnum("anEnum").equals(SomeEnum.LABEL1));
        assertArrayEquals(new Class<?>[] {String.class}, (Class<?>[]) nestedAnno.get("classArray"));

        AnnotationAttributes[] nestedAnnoArray = specialAttrs.getAnnotationArray("nestedAnnoArray");
        assertThat(nestedAnnoArray.length, is(2));
        assertThat(nestedAnnoArray[0].getString("value"), is("default"));
        assertTrue(nestedAnnoArray[0].getEnum("anEnum").equals(SomeEnum.DEFAULT));
        assertArrayEquals(new Class<?>[] {Void.class}, (Class<?>[]) nestedAnnoArray[0].get("classArray"));
        assertThat(nestedAnnoArray[1].getString("value"), is("na1"));
        assertTrue(nestedAnnoArray[1].getEnum("anEnum").equals(SomeEnum.LABEL2));
        assertArrayEquals(new Class<?>[] {Number.class}, (Class<?>[]) nestedAnnoArray[1].get("classArray"));
        assertArrayEquals(new Class<?>[] {Number.class}, nestedAnnoArray[1].getClassArray("classArray"));

        AnnotationAttributes optional = specialAttrs.getAnnotation("optional");
        assertThat(optional.getString("value"), is("optional"));
        assertTrue(optional.getEnum("anEnum").equals(SomeEnum.DEFAULT));
        assertArrayEquals(new Class<?>[] {Void.class}, (Class<?>[]) optional.get("classArray"));
        assertArrayEquals(new Class<?>[] {Void.class}, optional.getClassArray("classArray"));

        AnnotationAttributes[] optionalArray = specialAttrs.getAnnotationArray("optionalArray");
        assertThat(optionalArray.length, is(1));
        assertThat(optionalArray[0].getString("value"), is("optional"));
        assertTrue(optionalArray[0].getEnum("anEnum").equals(SomeEnum.DEFAULT));
        assertArrayEquals(new Class<?>[] {Void.class}, (Class<?>[]) optionalArray[0].get("classArray"));
        assertArrayEquals(new Class<?>[] {Void.class}, optionalArray[0].getClassArray("classArray"));

        assertEquals("direct", metadata.getAnnotationAttributes(MetadataTests.DirectAnnotation.class.getName()).get("value"));
        allMeta = metadata.getAllAnnotationAttributes(MetadataTests.DirectAnnotation.class.getName()).get("value");
        assertThat(new HashSet<>(allMeta), is(equalTo(new HashSet<Object>(Arrays.asList("direct", "meta")))));
        allMeta = metadata.getAllAnnotationAttributes(MetadataTests.DirectAnnotation.class.getName()).get("additional");
        assertThat(new HashSet<>(allMeta), is(equalTo(new HashSet<Object>(Arrays.asList("direct")))));
    }

}
