package com.spring.common.annotation;

import java.lang.annotation.*;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/24 11:51
 */
public class AnnotatedModel2 {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Inherited
    public @interface Transactional2 {

        String value() default "transactional";

        String qualifier() default "transactionManager";

        boolean readOnly() default false;
    }


    @Transactional2(qualifier ="TxConfig")
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Composed {
    }

    @Transactional2(qualifier ="DerivedTxConfig")
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Composed2 {
    }

    @Transactional2(qualifier ="TxConfig")
    public static class MultipleAnnotationAttrRewrite {
    }

    @Transactional2(qualifier ="DerivedTxConfig")
    public static class SubMultipleAnnotationAttrRewrite extends MultipleAnnotationAttrRewrite {
    }


    @Composed
    public static class MultipleAnnotationAttrRewrite2 {
    }

    @Transactional2(qualifier ="DerivedTxConfig")
    public static class SubMultipleAnnotationAttrRewrite2 extends MultipleAnnotationAttrRewrite2 {
    }

    @Composed2
    public static class SubMultipleAnnotationAttrRewrite3 extends MultipleAnnotationAttrRewrite2 {
    }

    @Composed
    @Composed2
    public static class MultipleAnnotationAttrComposed1 {
    }


    @Transactional2(qualifier ="TxConfig")
    public static class ClassWithInheritedAnnotation {
    }
    @Composed2
    public static class MultipleAnnotationAttrComposed2 extends ClassWithInheritedAnnotation{
    }
}
