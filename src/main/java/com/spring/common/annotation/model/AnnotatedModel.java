package com.spring.common.annotation.model;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/22 18:18
 */
public class AnnotatedModel {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Inherited
    public @interface Transactional {

        String value() default "";

        String qualifier() default "transactionManager";

        boolean readOnly() default false;
    }

    @Transactional(qualifier = "composed1")
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface InheritedComposed {
    }

    @Transactional(qualifier = "composed2", readOnly = true)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Composed {
    }

    @Transactional
    @Component
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ComposedTransactional {
    }

    @ComposedTransactional
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ComposedComposedTransactional {
    }


    public static class NonAnnotatedClass {
    }

    @ComposedTransactional
    public interface ComposedTransactionalInterface{
    }

    @ComposedTransactional
    public static class ComposedTransactionalClass {
    }

    public static class implementComposedTransactionalInterfaceClass implements ComposedTransactionalInterface{

    }

    public static class SubTransactionalComponentClass extends ComposedTransactionalClass {
    }

    @ComposedComposedTransactional
    public static class ComposedComposedTransactionalClass {
    }

}
