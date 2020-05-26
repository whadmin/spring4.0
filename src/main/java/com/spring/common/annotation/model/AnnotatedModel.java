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

    @Transactional
    @Component
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ComposedTransactional {
    }


    @ComposedTransactional
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface ComposedComposedTransactionalInherited {
    }


    public static class NonAnnotatedClass {
    }

    @ComposedTransactional
    public static class ComposedTransactionalClass {
    }

    public static class SubComposedTransactionalClass extends ComposedTransactionalClass {
    }

    @ComposedComposedTransactionalInherited
    public static class ComposedComposedTransactionalClass {
    }

    public static class SubComposedComposedTransactionalClass extends ComposedComposedTransactionalClass {
    }

    public static class SubSubComposedComposedTransactionalClass extends SubComposedComposedTransactionalClass {
    }

    @ComposedTransactional
    @ComposedComposedTransactionalInherited
    public interface ComposedTransactionalInterface{
    }

    public interface SubComposedTransactionalInterface extends ComposedTransactionalInterface{
    }

    public static class implementComposedTransactionalInterfaceClass implements ComposedTransactionalInterface{
    }
}
