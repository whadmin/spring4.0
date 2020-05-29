package com.spring.common.annotation.model;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/26 14:38
 */
public class AnnotatedModel3 {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface ContextConfig {

        @AliasFor(attribute = "locations")
        String[] value() default {"default"};

        @AliasFor(attribute = "value")
        String[] locations() default {"default"};
    }

    @ContextConfig(value ="composedContextConfig")
    //@ContextConfig(locations ="composedContextConfig")
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ComposedContextConfig {

    }

    @ContextConfig
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ComposedContextConfig1 {

        @AliasFor(annotation = ContextConfig.class, attribute = "locations")
        String[] composedlocations() default {};

        @AliasFor(annotation = ContextConfig.class, attribute = "value")
        String[] composedvalue() default {};
    }

    @ContextConfig
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ComposedContextConfig2 {

        @AliasFor(annotation = ContextConfig.class, attribute = "locations")
        String composedlocations() default "";

        @AliasFor(annotation = ContextConfig.class, attribute = "value")
        String composedvalue() default "";
    }

    @ContextConfig("test")
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ComposedContextConfig3 {

        @AliasFor(annotation = ContextConfig.class, attribute = "locations")
        String composedlocations() default "";

        @AliasFor(annotation = ContextConfig.class, attribute = "value")
        String composedvalue() default "";
    }

    @ContextConfig(value = "test")
    @Retention(RetentionPolicy.RUNTIME)
    @interface ComposedContextConfig4 {

        String[] locations();
    }

    @ComposedContextConfig1
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ComposedComposedContextConfig {

        @AliasFor(annotation = ComposedContextConfig1.class, attribute = "composedlocations")
        String[] composedomposedlocations() default {};

        @AliasFor(annotation = ComposedContextConfig1.class, attribute = "composedvalue")
        String[] composedcomposedvalue() default {};
    }

    @ComposedContextConfig
    public static class ComposedContextConfigClass {
    }

    @ComposedContextConfig1(composedlocations = "composedContextConfig")
    public static class ComposedContextConfig1Class1 {
    }
    @ComposedContextConfig1(composedvalue = "composedContextConfig")
    public static class ComposedContextConfig1Class2 {
    }

    @ComposedContextConfig2(composedlocations = "composedContextConfig")
    public static class ComposedContextConfig2Class1 {
    }

    @ComposedContextConfig2(composedvalue = "composedContextConfig")
    public static class ComposedContextConfig2Class2 {
    }

    @ComposedContextConfig3(composedlocations = "composedContextConfig")
    public static class ComposedContextConfig3Class1 {
    }

    @ComposedContextConfig3(composedvalue = "composedContextConfig")
    public static class ComposedContextConfig3Class2 {
    }

    @ComposedContextConfig4(locations = "composedContextConfig")
    public static class ComposedContextConfig4Class {
    }

    @ComposedComposedContextConfig(composedomposedlocations = "composedContextConfig")
    public static class ComposedComposedContextConfigClass1{
    }

    @ComposedComposedContextConfig(composedcomposedvalue = "composedContextConfig")
    public static class ComposedComposedContextConfigClass2{
    }
}
