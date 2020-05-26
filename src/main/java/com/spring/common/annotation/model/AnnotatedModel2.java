package com.spring.common.annotation.model;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/24 11:51
 */
public class AnnotatedModel2 {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Inherited
    public @interface Transactional {

        String value() default "default";
    }


    @Transactional("composedTransactional1")
    @Component("composedTransactional1")
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface ComposedTransactional1 {

        String value() default "";
    }

    @Transactional("composedTransactional2")
    @Component("composedTransactional2")
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface ComposedTransactional2 {

        String value() default "";
    }

    @Transactional("composedTransactionalNoInherited")
    @Component("composedTransactional1")
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ComposedTransactionalNoInherited {
    }

    @Transactional("composedTransactionalInherited")
    @Component("composedTransactional1")
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface ComposedTransactionalInherited {
    }

    /*****************************注解装饰类*****************************/

    public static class NonAnnotatedClass {
    }

    @Transactional
    public static class TransactionalClass {
    }


    @ComposedTransactional1
    public static class ComposedTransactionalClass1 {
    }

    @ComposedTransactional2
    public static class ComposedTransactionalClass2 {
    }

    /** 类被指定的注解@Transactional多次修饰 **/

    @ComposedTransactional1
    @ComposedTransactional2
    public static class MultipleAnnotationClass1 {
    }

    @ComposedTransactional1
    @Transactional
    public static class MultipleAnnotationClass2 {
    }

    @ComposedTransactional2
    @Transactional
    public static class MultipleAnnotationClass3 {
    }

    /** 类被指定的注解@Transactional 在父类子类之间多次修饰 **/

    /**
     * 子类的直接注解和父类元注解相同属性合并
     **/
    @Transactional("sub")
    public static class SubComposedTransactionalClass1 extends ComposedTransactionalClass1 {
    }

    @Transactional("sub")
    public static class SubComposedTransactionalClass2 extends ComposedTransactionalClass2 {
    }

    /**
     * 子类的元注解和父类元注解相同属性合并
     **/
    @ComposedTransactional2
    public static class SubComposedTransactionalClass3 extends ComposedTransactionalClass1 {
    }

    @ComposedTransactional1
    public static class SubComposedTransactionalClass4 extends ComposedTransactionalClass2 {
    }

    /**
     * 子类的元注解和父类注解相同属性合并
     **/
    @ComposedTransactional1
    public static class SubTransactionalClass1 extends TransactionalClass {
    }

    @ComposedTransactional2
    public static class SubTransactionalClass2 extends TransactionalClass {
    }

    /**
     * 子类的注解和父类注解相同属性覆盖
     **/
    @Transactional("sub")
    public static class SubTransactionalClass3 extends TransactionalClass {
    }


    @ComposedTransactionalNoInherited
    public static class ComposedTransactionalInheritedClass1 {
    }

    @ComposedTransactionalInherited
    public static class ComposedTransactionalInheritedClass2 {
    }

    /**
     * 不能从非@Inherited父类注解继承
     **/
    public static class SubComposedTransactionalInheritedClass1 extends ComposedTransactionalInheritedClass1 {
    }

    /**
     * 从@Inherited父类注解继承
     **/
    public static class SubComposedTransactionalInheritedClass2 extends ComposedTransactionalInheritedClass2 {
    }


    @ComposedTransactional1("composedTransactional1")
    @ComposedTransactional2("composedTransactional2")
    public interface ComposedTransactionalInterface{
    }

    public interface SubComposedTransactionalInterface extends AnnotatedModel2.ComposedTransactionalInterface {
    }

    public static class implementComposedTransactionalInterfaceClass implements AnnotatedModel2.ComposedTransactionalInterface {
    }


}
