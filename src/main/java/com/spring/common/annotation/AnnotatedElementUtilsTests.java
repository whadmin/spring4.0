package com.spring.common.annotation;

import org.junit.Test;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.lang.annotation.*;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.core.annotation.AnnotatedElementUtils.*;
import static org.springframework.core.annotation.AnnotatedElementUtils.getAllAnnotationAttributes;

public class AnnotatedElementUtilsTests {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Inherited
    @interface Transactional {

        String value() default "";

        String qualifier() default "transactionManager";

        boolean readOnly() default false;
    }

    @Transactional
    @Component
    @Retention(RetentionPolicy.RUNTIME)
    @interface TransactionalComponent {
    }

    @TransactionalComponent
    @Retention(RetentionPolicy.RUNTIME)
    @interface ComposedTransactionalComponent {
    }

    static class NonAnnotatedClass {
    }

    @TransactionalComponent
    static class TransactionalComponentClass {
    }

    @ComposedTransactionalComponent
    static class ComposedTransactionalComponentClass {
    }

    static class SubTransactionalComponentClass extends TransactionalComponentClass {
    }

    /**
     * 获取类中的自定义的元注解集合（支持嵌套注解） 元注解：没有嵌套自定义注解
     */
    @Test
    public void getMetaAnnotationTypesOnClassWithMetaDepth1() {
        assertNull(getMetaAnnotationTypes(NonAnnotatedClass.class, TransactionalComponent.class));
        assertNull(getMetaAnnotationTypes(NonAnnotatedClass.class, TransactionalComponent.class.getName()));

        Set<String> names = getMetaAnnotationTypes(TransactionalComponentClass.class, TransactionalComponent.class);
        assertEquals(names(Transactional.class, Component.class), names);

        names = getMetaAnnotationTypes(TransactionalComponentClass.class, TransactionalComponent.class.getName());
        assertEquals(names(Transactional.class, Component.class), names);

        names = getMetaAnnotationTypes(ComposedTransactionalComponentClass.class, ComposedTransactionalComponent.class);
        assertEquals(names(TransactionalComponent.class, Transactional.class, Component.class), names);

        names = getMetaAnnotationTypes(ComposedTransactionalComponentClass.class, ComposedTransactionalComponent.class.getName());
        assertEquals(names(TransactionalComponent.class, Transactional.class, Component.class), names);
    }

    /**
     * 判断类中的是否存在指定元注解  元注解：没有嵌套自定义注解
     */
    @Test
    public void hasMetaAnnotationTypesOnClassWithMetaDepth0() {
        assertFalse(hasMetaAnnotationTypes(NonAnnotatedClass.class, Transactional.class.getName()));
        assertFalse(hasMetaAnnotationTypes(TransactionalComponentClass.class, TransactionalComponent.class.getName()));
        assertFalse(hasMetaAnnotationTypes(ComposedTransactionalComponentClass.class, ComposedTransactionalComponent.class.getName()));


        assertTrue(hasMetaAnnotationTypes(TransactionalComponentClass.class, Transactional.class.getName()));
        assertTrue(hasMetaAnnotationTypes(TransactionalComponentClass.class, Component.class.getName()));

        assertTrue(hasMetaAnnotationTypes(ComposedTransactionalComponentClass.class, Transactional.class.getName()));
        assertTrue(hasMetaAnnotationTypes(ComposedTransactionalComponentClass.class, Component.class.getName()));
    }

    /**
     * 判断类中的是否存在指定注解  支持子类从父类继承父类注解
     */
    @Test
    public void isAnnotatedOnNonAnnotatedClass() {
        assertFalse(isAnnotated(NonAnnotatedClass.class, Transactional.class.getName()));
        assertTrue(isAnnotated(TransactionalComponentClass.class, TransactionalComponent.class.getName()));
        assertFalse("isAnnotated() does not search the class hierarchy.",
                isAnnotated(SubTransactionalComponentClass.class, TransactionalComponent.class.getName()));
        assertTrue(isAnnotated(TransactionalComponentClass.class, Transactional.class.getName()));
        assertTrue(isAnnotated(TransactionalComponentClass.class, Component.class.getName()));
        assertTrue(isAnnotated(ComposedTransactionalComponentClass.class, Transactional.class.getName()));
        assertTrue(isAnnotated(ComposedTransactionalComponentClass.class, Component.class.getName()));
        assertTrue(isAnnotated(ComposedTransactionalComponentClass.class, ComposedTransactionalComponent.class.getName()));
    }


    @Transactional
    public interface InheritedAnnotationInterface {
    }

    public interface SubInheritedAnnotationInterface extends InheritedAnnotationInterface {
    }

    public interface SubSubInheritedAnnotationInterface extends SubInheritedAnnotationInterface {
    }

    static abstract class AbstractClassWithInheritedInterfaceAnnotation<T> implements InheritedAnnotationInterface {
    }

    static class ClassWithInheritedInterfaceAnnotation implements InheritedAnnotationInterface {
    }

    /**
     * 1 接口声明的注解可以同时被
     * getMergedAnnotationAttributes
     * findMergedAnnotationAttributes
     * getAllAnnotationAttributes
     * 获取到注解属性
     * <p>
     * 2 子接口想获取父接口中声明的注解只能通过
     * findMergedAnnotationAttributes
     * 【即使注解被定义成了@Inherited】
     */
    @Test
    public void annotationAttributesOnInheritedAnnotationInterface() {
        AnnotationAttributes attributes = null;
        MultiValueMap attributes1 = null;
        attributes = getMergedAnnotationAttributes(InheritedAnnotationInterface.class, Transactional.class);
        assertNotNull("Should find @Order on NonInheritedAnnotationInterface", attributes);
        attributes = findMergedAnnotationAttributes(InheritedAnnotationInterface.class, Transactional.class);
        assertNotNull("Should find @Order on NonInheritedAnnotationInterface", attributes);
        attributes1 = getAllAnnotationAttributes(InheritedAnnotationInterface.class, Order.class.getName());
        assertNotNull("Should find @Order on NonInheritedAnnotationInterface", attributes1);

        attributes = getMergedAnnotationAttributes(SubInheritedAnnotationInterface.class, Transactional.class);
        assertNull("Should find @Order on NonInheritedAnnotationInterface", attributes);
        attributes = findMergedAnnotationAttributes(SubInheritedAnnotationInterface.class, Transactional.class);
        assertNotNull("Should find @Transactional on SubInheritedAnnotationInterface", attributes);
        attributes1 = getAllAnnotationAttributes(SubInheritedAnnotationInterface.class, Transactional.class.getName());
        assertNull("Should find @Order on NonInheritedAnnotationInterface", attributes1);

        attributes = getMergedAnnotationAttributes(SubSubInheritedAnnotationInterface.class, Transactional.class);
        assertNull("Should find @Order on NonInheritedAnnotationInterface", attributes);
        attributes = findMergedAnnotationAttributes(SubSubInheritedAnnotationInterface.class, Transactional.class);
        assertNotNull("Should find @Transactional on SubInheritedAnnotationInterface", attributes);
        attributes1 = getAllAnnotationAttributes(SubSubInheritedAnnotationInterface.class, Transactional.class.getName());
        assertNull("Should find @Order on NonInheritedAnnotationInterface", attributes1);

        attributes = getMergedAnnotationAttributes(AbstractClassWithInheritedInterfaceAnnotation.class, Transactional.class);
        assertNull("Should not find @Transactional on ConcreteClassWithInheritedAnnotation", attributes);
        attributes = findMergedAnnotationAttributes(AbstractClassWithInheritedInterfaceAnnotation.class, Transactional.class);
        assertNotNull("Should not find @Transactional on ConcreteClassWithInheritedAnnotation", attributes);
        attributes1 = getAllAnnotationAttributes(AbstractClassWithInheritedInterfaceAnnotation.class, Transactional.class.getName());
        assertNull("Should find @Order on NonInheritedAnnotationInterface", attributes1);

        attributes = getMergedAnnotationAttributes(ClassWithInheritedInterfaceAnnotation.class, Transactional.class);
        assertNull("Should not find @Transactional on ConcreteClassWithInheritedAnnotation", attributes);
        attributes = findMergedAnnotationAttributes(ClassWithInheritedInterfaceAnnotation.class, Transactional.class);
        assertNotNull("Should not find @Transactional on ConcreteClassWithInheritedAnnotation", attributes);
        attributes1 = getAllAnnotationAttributes(ClassWithInheritedInterfaceAnnotation.class, Transactional.class.getName());
        assertNull("Should find @Order on NonInheritedAnnotationInterface", attributes1);
    }

    @Transactional("TxConfig")
    static class TxConfig {
    }

    @Transactional("DerivedTxConfig")
    static class DerivedTxConfig extends TxConfig {
    }

    /**
     * 类声明的注解可以同时被
     * getMergedAnnotationAttributes
     * findMergedAnnotationAttributes
     * getAllAnnotationAttributes
     * 获取到注解属性
     * <p>
     * 子类可以从父类继承被声明为@Inherited注解，如果子类被声明了相同
     * getMergedAnnotationAttributes  子类如果被声明了相同的注解则子类覆盖父类注解
     * findMergedAnnotationAttributes 子类如果被声明了相同的注解则子类覆盖父类注解
     * getAllAnnotationAttributes     子类如果被声明了相同的注解则子类覆盖父类注解
     */
    @Test
    public void annotationAttributesOnTxConfig() {
        AnnotationAttributes attributes = null;
        MultiValueMap attributes1 = null;
        attributes = getMergedAnnotationAttributes(TxConfig.class, Transactional.class);
        assertNotNull("Annotation attributes map for @Transactional on TxConfig", attributes);
        assertEquals("value for TxConfig.", "TxConfig", attributes.get("value"));
        attributes = findMergedAnnotationAttributes(TxConfig.class, Transactional.class);
        assertNotNull("Annotation attributes map for @Transactional on TxConfig", attributes);
        assertEquals("value for TxConfig.", "TxConfig", attributes.get("value"));
        attributes1 = getAllAnnotationAttributes(TxConfig.class, Transactional.class.getName());
        assertNotNull("Annotation attributes map for @Transactional on TxConfig", attributes1);
        assertEquals("value for TxConfig.", asList("TxConfig"), attributes1.get("value"));

        attributes = getMergedAnnotationAttributes(DerivedTxConfig.class, Transactional.class);
        assertNotNull("Annotation attributes map for @Transactional on TxConfig", attributes);
        assertEquals("value for TxConfig.", "DerivedTxConfig", attributes.get("value"));
        attributes = findMergedAnnotationAttributes(DerivedTxConfig.class, Transactional.class);
        assertNotNull("Annotation attributes map for @Transactional on TxConfig", attributes);
        assertEquals("value for TxConfig.", "DerivedTxConfig", attributes.get("value"));
        attributes1 = getAllAnnotationAttributes(DerivedTxConfig.class, Transactional.class.getName());
        assertNotNull("Annotation attributes map for @Transactional on TxConfig", attributes1);
        assertEquals("value for TxConfig.", asList("DerivedTxConfig"), attributes1.get("value"));
    }

    @Transactional(qualifier = "composed2", readOnly = true)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Composed {
    }


    @Transactional
    static class ClassWithInheritedAnnotation {
    }

    @Composed
    static class SubClassWithInheritedAnnotation extends ClassWithInheritedAnnotation {
    }

    /**
     * 子类可以从父类继承被声明为@Inherited注解，如果子类被声明了相同的注解的父注解和父类继承的注解相同
     * getMergedAnnotationAttributes  子类被声明了相同的注解的父注解和父类继承的注解相同，则子类覆盖父类注解
     * findMergedAnnotationAttributes 子类被声明了相同的注解的父注解和父类继承的注解相同，则子类覆盖父类注解
     * getAllAnnotationAttributes     子类被声明了相同的注解的父注解和父类继承的注解相同，想两个注解的属性值合并
     */
    @Test
    public void annotationAttributesOnSubClassWithInheritedAnnotation() {
        AnnotationAttributes attributes = null;
        MultiValueMap attributes1 = null;
        attributes1 = getAllAnnotationAttributes(SubClassWithInheritedAnnotation.class, Transactional.class.getName());
        assertNotNull("Annotation attributes map for @Transactional on SubClassWithInheritedAnnotation", attributes1);
        assertEquals(asList(Boolean.TRUE, Boolean.FALSE), attributes1.get("readOnly"));

        attributes = getMergedAnnotationAttributes(SubClassWithInheritedAnnotation.class, Transactional.class);
        assertNotNull("Annotation attributes map for @Transactional on SubClassWithInheritedAnnotation", attributes);
        assertEquals(Boolean.TRUE, attributes.get("readOnly"));

        attributes = findMergedAnnotationAttributes(SubClassWithInheritedAnnotation.class, Transactional.class);
        assertNotNull("Annotation attributes map for @Transactional on SubClassWithInheritedAnnotation", attributes);
        assertEquals(Boolean.TRUE, attributes.get("readOnly"));
    }

    static class SubSubClassWithInheritedAnnotation extends SubClassWithInheritedAnnotation {
    }

    @Test
    public void annotationAttributesOnSubSubClassWithInheritedAnnotation() {
        AnnotationAttributes attributes = null;
        MultiValueMap attributes1 = null;
        attributes1 = getAllAnnotationAttributes(SubSubClassWithInheritedAnnotation.class, Transactional.class.getName());
        assertNotNull("Annotation attributes map for @Transactional on SubClassWithInheritedAnnotation", attributes1);
        assertEquals(asList(Boolean.FALSE), attributes1.get("readOnly"));

        attributes = getMergedAnnotationAttributes(SubSubClassWithInheritedAnnotation.class, Transactional.class);
        assertNotNull("Annotation attributes map for @Transactional on SubClassWithInheritedAnnotation", attributes);
        assertEquals(Boolean.FALSE, attributes.get("readOnly"));

        attributes = findMergedAnnotationAttributes(SubSubClassWithInheritedAnnotation.class, Transactional.class);
        assertNotNull("Annotation attributes map for @Transactional on SubClassWithInheritedAnnotation", attributes);
        assertEquals(Boolean.TRUE, attributes.get("readOnly"));
    }


    @Transactional(qualifier = "composed1")
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    @interface InheritedComposed {
    }

    @InheritedComposed
    static class ClassWithInheritedComposedAnnotation {
    }

    @Composed
    static class SubClassWithInheritedComposedAnnotation extends ClassWithInheritedComposedAnnotation {
    }

    static class SubSubClassWithInheritedComposedAnnotation extends SubClassWithInheritedComposedAnnotation {
    }

    @Test
    public void annotationAttributesOnSubSubClassWithInheritedComposedAnnotation() {
        AnnotationAttributes attributes = null;
        MultiValueMap attributes1 = null;
        attributes = getMergedAnnotationAttributes(SubSubClassWithInheritedComposedAnnotation.class, Transactional.class);
        assertNotNull("Annotation attributes map for @Transactional on SubSubClassWithInheritedComposedAnnotation", attributes);
        assertEquals(Boolean.FALSE, attributes.get("readOnly"));

        attributes = findMergedAnnotationAttributes(SubSubClassWithInheritedComposedAnnotation.class, Transactional.class);
        assertNotNull("Annotation attributes map for @Transactional on SubClassWithInheritedAnnotation", attributes);
        assertEquals(Boolean.TRUE, attributes.get("readOnly"));

        attributes1 = getAllAnnotationAttributes(SubSubClassWithInheritedComposedAnnotation.class, Transactional.class.getName());
        assertNotNull("Annotation attributes map for @Transactional on SubClassWithInheritedAnnotation", attributes1);
        assertEquals(asList(Boolean.FALSE), attributes1.get("readOnly"));
    }

    @Transactional("TxInheritedComposed")
    @Retention(RetentionPolicy.RUNTIME)
    @interface TxInheritedComposed {
    }

    @Transactional("TxComposed")
    @Retention(RetentionPolicy.RUNTIME)
    @interface TxComposed {
    }

    @TxInheritedComposed
    @TxComposed
    static class TxFromMultipleComposedAnnotations {
    }

    @Test
    public void annotationAttributesOnTxFromMultipleComposedAnnotations() {
        AnnotationAttributes attributes = null;
        MultiValueMap attributes1 = null;
        attributes1 = getAllAnnotationAttributes(TxFromMultipleComposedAnnotations.class, Transactional.class.getName());
        assertNotNull("Annotation attributes map for @Transactional on TxFromMultipleComposedAnnotations", attributes1);
        assertEquals("value for TxFromMultipleComposedAnnotations.", asList("TxInheritedComposed", "TxComposed"), attributes1.get("value"));

        attributes = getMergedAnnotationAttributes(TxFromMultipleComposedAnnotations.class, Transactional.class);
        assertNotNull("Annotation attributes map for @Transactional on TxFromMultipleComposedAnnotations", attributes);
        assertEquals("value for TxFromMultipleComposedAnnotations.", "TxInheritedComposed", attributes.get("value"));

        attributes = findMergedAnnotationAttributes(TxFromMultipleComposedAnnotations.class, Transactional.class);
        assertNotNull("Annotation attributes map for @Transactional on TxFromMultipleComposedAnnotations", attributes);
        assertEquals("value for TxFromMultipleComposedAnnotations.", "TxInheritedComposed", attributes.get("value"));
    }


    @Transactional
    static interface InterfaceWithInheritedAnnotation {

        @Order
        void handleFromInterface();
    }

    static abstract class AbstractClassWithInheritedAnnotation<T> implements InterfaceWithInheritedAnnotation {

        @Transactional
        public abstract void handle();

        @Transactional
        public void handleParameterized(T t) {
        }
    }

    static class ConcreteClassWithInheritedAnnotation extends AbstractClassWithInheritedAnnotation<String> {

        @Override
        public void handle() {
        }

        @Override
        public void handleParameterized(String s) {
        }

        @Override
        public void handleFromInterface() {
        }
    }

    @Test
    public void findMergedAnnotationAttributesInheritedFromInterfaceMethod() throws NoSuchMethodException {
        Method method = ConcreteClassWithInheritedAnnotation.class.getMethod("handleFromInterface");
        AnnotationAttributes attributes = findMergedAnnotationAttributes(method, Order.class);
        assertNotNull("Should find @Order on ConcreteClassWithInheritedAnnotation.handleFromInterface() method", attributes);

        method = ConcreteClassWithInheritedAnnotation.class.getMethod("handle");
        attributes = findMergedAnnotationAttributes(method, Transactional.class);
        assertNotNull("Should find @Transactional on ConcreteClassWithInheritedAnnotation.handle() method", attributes);

        method = ConcreteClassWithInheritedAnnotation.class.getMethod("handleParameterized", String.class);
        attributes = findMergedAnnotationAttributes(method, Transactional.class);
        assertNull("Should not find @Transactional on bridged ConcreteClassWithInheritedAnnotation.handleParameterized()", attributes);
    }


    public interface GenericParameter<T> {

        T getFor(Class<T> cls);
    }

    @SuppressWarnings("unused")
    private static class StringGenericParameter implements GenericParameter<String> {

        @Order
        @Override
        public String getFor(Class<String> cls) {
            return "foo";
        }

        public String getFor(Integer integer) {
            return "foo";
        }
    }

    @Test
    public void findMergedAnnotationAttributesFromBridgeMethod() throws NoSuchMethodException {
        Method[] methods = StringGenericParameter.class.getMethods();
        Method bridgeMethod = null;
        Method bridgedMethod = null;

        for (Method method : methods) {
            if ("getFor".equals(method.getName()) && !method.getParameterTypes()[0].equals(Integer.class)) {
                if (method.getReturnType().equals(Object.class)) {
                    bridgeMethod = method;
                }
                else {
                    bridgedMethod = method;
                }
            }
        }
        assertTrue(bridgeMethod != null && bridgeMethod.isBridge());
        assertTrue(bridgedMethod != null && !bridgedMethod.isBridge());

        AnnotationAttributes attributes = findMergedAnnotationAttributes(bridgeMethod, Order.class);
        assertNotNull("Should find @Order on StringGenericParameter.getFor() bridge method", attributes);
    }



    private AnnotationAttributes findMergedAnnotationAttributes(AnnotatedElement element, Class<? extends Annotation> annotationType) {
        Assert.notNull(annotationType, "annotationType must not be null");
        return AnnotatedElementUtils.findMergedAnnotationAttributes(element, annotationType.getName(), false, false);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface ContextConfig {

        @AliasFor(attribute = "locations")
        String[] value() default {};

        @AliasFor(attribute = "value")
        String[] locations() default {};

        Class<?>[] classes() default {};
    }

    @ContextConfig
    @Retention(RetentionPolicy.RUNTIME)
    @interface ConventionBasedComposedContextConfig {

        String[] locations() default {};
    }

    @ContextConfig(value = "duplicateDeclaration")
    @Retention(RetentionPolicy.RUNTIME)
    @interface InvalidConventionBasedComposedContextConfig {

        String[] locations();
    }

    @ContextConfig
    @Retention(RetentionPolicy.RUNTIME)
    @interface HalfConventionBasedAndHalfAliasedComposedContextConfig {

        String[] locations() default {};

        @AliasFor(annotation = ContextConfig.class, attribute = "locations")
        String[] xmlConfigFiles() default {};
    }

    @ContextConfig
    @Retention(RetentionPolicy.RUNTIME)
    @interface AliasedComposedContextConfig {

        @AliasFor(annotation = ContextConfig.class, attribute = "locations")
        String[] xmlConfigFiles();
    }

    @ContextConfig
    @Retention(RetentionPolicy.RUNTIME)
    @interface AliasedValueComposedContextConfig {

        @AliasFor(annotation = ContextConfig.class, attribute = "value")
        String[] locations();
    }

    @ConventionBasedComposedContextConfig(locations = "explicitDeclaration")
    static class ConventionBasedComposedContextConfigClass {
    }

    @InvalidConventionBasedComposedContextConfig(locations = "requiredLocationsDeclaration")
    static class InvalidConventionBasedComposedContextConfigClass {
    }

    @HalfConventionBasedAndHalfAliasedComposedContextConfig(xmlConfigFiles = "explicitDeclaration")
    static class HalfConventionBasedAndHalfAliasedComposedContextConfigClassV1 {
    }

    @HalfConventionBasedAndHalfAliasedComposedContextConfig(locations = "explicitDeclaration")
    static class HalfConventionBasedAndHalfAliasedComposedContextConfigClassV2 {
    }

    @AliasedComposedContextConfig(xmlConfigFiles = "test.xml")
    static class AliasedComposedContextConfigClass {
    }

    @AliasedValueComposedContextConfig(locations = "test.xml")
    static class AliasedValueComposedContextConfigClass {
    }


    @ContextConfig
    @Retention(RetentionPolicy.RUNTIME)
    @interface ImplicitAliasesContextConfig {

        @AliasFor(annotation = ContextConfig.class, attribute = "locations")
        String[] groovyScripts() default {};

        @AliasFor(annotation = ContextConfig.class, attribute = "locations")
        String[] xmlFiles() default {};

        // intentionally omitted: attribute = "locations"
        @AliasFor(annotation = ContextConfig.class)
        String[] locations() default {};

        // intentionally omitted: attribute = "locations" (SPR-14069)
        @AliasFor(annotation = ContextConfig.class)
        String[] value() default {};
    }

    @ImplicitAliasesContextConfig(xmlFiles = {"A.xml", "B.xml"})
    @Retention(RetentionPolicy.RUNTIME)
    @interface ComposedImplicitAliasesContextConfig {
    }

    @ComposedImplicitAliasesContextConfig
    static class ComposedImplicitAliasesContextConfigClass {
    }


    @Test
    public void getMergedAnnotationAttributesOnClassWithLocalAnnotation1() {


        AnnotationAttributes attributes = getMergedAnnotationAttributes(ConventionBasedComposedContextConfigClass.class, ContextConfig.class.getName());
        assertNotNull("Should find @ContextConfig on " + ConventionBasedComposedContextConfigClass.class.getSimpleName(), attributes);
        assertArrayEquals("locations", asArray("explicitDeclaration"), attributes.getStringArray("locations"));
        assertArrayEquals("value", asArray("explicitDeclaration"), attributes.getStringArray("value"));


        for (Class<?> clazz : asList(HalfConventionBasedAndHalfAliasedComposedContextConfigClassV1.class,
                HalfConventionBasedAndHalfAliasedComposedContextConfigClassV2.class)) {
            attributes = getMergedAnnotationAttributes(clazz, ContextConfig.class.getName());
            assertNotNull("Should find @ContextConfig on " + clazz.getSimpleName(), attributes);
            assertArrayEquals("locations for class [" + clazz.getSimpleName() + "]", asArray("explicitDeclaration"), attributes.getStringArray("locations"));
            assertArrayEquals("value for class [" + clazz.getSimpleName() + "]", asArray("explicitDeclaration"), attributes.getStringArray("value"));
        }


        attributes = getMergedAnnotationAttributes(AliasedComposedContextConfigClass.class, ContextConfig.class.getName());
        assertNotNull("Should find @ContextConfig on " + AliasedComposedContextConfigClass.class.getSimpleName(), attributes);
        assertArrayEquals("value", asArray("test.xml"), attributes.getStringArray("value"));
        assertArrayEquals("locations", asArray("test.xml"), attributes.getStringArray("locations"));

        attributes = getMergedAnnotationAttributes(AliasedValueComposedContextConfigClass.class, ContextConfig.class.getName());
        assertNotNull("Should find @ContextConfig on " + AliasedComposedContextConfigClass.class.getSimpleName(), attributes);
        assertArrayEquals("value", asArray("test.xml"), attributes.getStringArray("value"));
        assertArrayEquals("locations", asArray("test.xml"), attributes.getStringArray("locations"));


        attributes = getMergedAnnotationAttributes(ComposedImplicitAliasesContextConfigClass.class, ImplicitAliasesContextConfig.class.getName());
        assertNotNull("Should find @ImplicitAliasesContextConfig on " + ComposedImplicitAliasesContextConfigClass.class.getSimpleName(), attributes);
        assertArrayEquals("groovyScripts", asArray("A.xml", "B.xml"), attributes.getStringArray("groovyScripts"));
        assertArrayEquals("xmlFiles", asArray("A.xml", "B.xml"), attributes.getStringArray("xmlFiles"));
        assertArrayEquals("locations", asArray("A.xml", "B.xml"), attributes.getStringArray("locations"));
        assertArrayEquals("value", asArray("A.xml", "B.xml"), attributes.getStringArray("value"));


    }

    @SafeVarargs
    static <T> T[] asArray(T... arr) {
        return arr;
    }

    private Set<String> names(Class<?>... classes) {
        return stream(classes).map(Class::getName).collect(toSet());
    }
}
