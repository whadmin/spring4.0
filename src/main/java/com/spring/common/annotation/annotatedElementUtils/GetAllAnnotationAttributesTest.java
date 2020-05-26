package com.spring.common.annotation.annotatedElementUtils;

import com.spring.common.annotation.model.AnnotatedModel;
import com.spring.common.annotation.model.AnnotatedModel2;
import org.junit.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.MultiValueMap;
import java.lang.reflect.AnnotatedElement;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.core.annotation.AnnotatedElementUtils.*;

/**
 * getAllAnnotationAttributes：用来获取{@link AnnotatedElement}装饰注解的属性
 *
 * 要知道装饰注解的属性，首先需要知道hasAnnotation能够获取该{@link AnnotatedElement}注解的范围
 *
 * 当{@link AnnotatedElement}用来表示Class类时，获取该{@link AnnotatedElement}注解的范围和{@link AnnotatedElementUtils#isAnnotated}一致包括：
 *
 *   1  该Class类上的修饰的注解
 *   2  该Class类上的修饰的注解同时也包括元注解
 *   3  该Class父类上标注了@Inherited元注解的注解
 *   4  该Class父类上标注了@Inherited元注解的注解的元注解
 *
 *  同时被指定注解可能存在多次装饰该注解类，这时一个属性可能存在多个值的情况：
 *
 *  在指定类中直接注解和元注解属性，元注解和元注解合并
 *  合并过程中优先级别  直接注解属性 > 元注解
 *
 *  如果存在父类继承的情况
 *   如下情况合并：
 *     指定类的直接注解和父类元注解
 *     指定类的元注解和父类元注解
 *     指定类的元注解和父类注解
 *   如下情况覆盖：
 *     指定类的直接注解和父类直接注解
 *
 * 当{@link AnnotatedElement}用来表示接口时，isAnnotationPresent获取其修饰注解包括：
 * <p>
 *   1 该interface上的修饰的注解
 *   2 该interface类上的修饰的注解同时也包括元注解（相对于JDKisAnnotationPresent改进）
 *   2 也不包含父接口上的注解（无论是否标注@Inherited元注解）
 */
public class GetAllAnnotationAttributesTest {

    private static final String TX_NAME = AnnotatedModel2.Transactional.class.getName();

    @Test
    public void getAllAnnotationAttributesOnNonAnnotatedClass() {
        assertThat(getMetaAnnotationTypes(AnnotatedModel.NonAnnotatedClass.class, AnnotatedModel2.Transactional.class).isEmpty()).isTrue();
    }

    /**
     * 当{@link AnnotatedElement}用来表示Class类时， 如果指定在类被指定的注解多次修饰
     */
    @Test
    public void getAllAnnotationAttributesOnAnnotatedClass() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.TransactionalClass.class,TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("value")).isEqualTo(asList("default"));

        MultiValueMap<String, Object> attributes2 = getAllAnnotationAttributes(AnnotatedModel2.ComposedTransactionalClass1.class,TX_NAME);
        assertThat(attributes2).isNotNull();
        assertThat(attributes2.get("value")).isEqualTo(asList("composedTransactional1"));

        MultiValueMap<String, Object> attributes3 = getAllAnnotationAttributes(AnnotatedModel2.ComposedTransactionalClass2.class,TX_NAME);
        assertThat(attributes3).isNotNull();
        assertThat(attributes3.get("value")).isEqualTo(asList("composedTransactional2"));
    }

    /**
     * 当{@link AnnotatedElement}用来表示Class类时， 如果指定在类被指定的注解多次修饰，其中直接注解和元注解会合并
     * 并按一定优先级别返回（类直接注解属性 > 类元注解（内部通过深度判定））
     */
    @Test
    public void getAllAnnotationAttributesOnMultipleAnnotatedClass() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.MultipleAnnotationClass1.class,TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("value")).isEqualTo(asList("composedTransactional1","composedTransactional2"));

        MultiValueMap<String, Object> attributes2 = getAllAnnotationAttributes(AnnotatedModel2.MultipleAnnotationClass2.class,TX_NAME);
        assertThat(attributes2).isNotNull();
        assertThat(attributes2.get("value")).isEqualTo(asList("default","composedTransactional1"));

        MultiValueMap<String, Object> attributes3 = getAllAnnotationAttributes(AnnotatedModel2.MultipleAnnotationClass3.class,TX_NAME);
        assertThat(attributes3).isNotNull();
        assertThat(attributes3.get("value")).isEqualTo(asList("default","composedTransactional2"));
    }

    /**
     * 当{@link AnnotatedElement}用来表示Class类时， 如果指定类存在父类，且指定的注解在类和父类中存在多次修饰，其中直接注解和元注解在如下情况合并
     *   指定类的直接注解和父类元注解相同属性合并
     *   指定类的元注解和父类元注解相同属性合并
     *   指定类的元注解和父类注解相同属性合并
     * 并按一定优先级别返回（子类直接注解>子类元注解>父类直接注解属性 > 父类元注解（内部通过深度判定））
     */
    @Test
    public void getAllAnnotationAttributesOnMultipleAnnotatedIncludeClassAndSuperClass() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.SubTransactionalClass1.class,TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("value")).isEqualTo(asList("composedTransactional1","default"));

        MultiValueMap<String, Object> attributes2 = getAllAnnotationAttributes(AnnotatedModel2.SubTransactionalClass2.class,TX_NAME);
        assertThat(attributes2).isNotNull();
        assertThat(attributes2.get("value")).isEqualTo(asList("composedTransactional2","default"));

        MultiValueMap<String, Object> attributes3 = getAllAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalClass1.class,TX_NAME);
        assertThat(attributes3).isNotNull();
        assertThat(attributes3.get("value")).isEqualTo(asList("sub","composedTransactional1"));

        MultiValueMap<String, Object> attributes4 = getAllAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalClass2.class,TX_NAME);
        assertThat(attributes4).isNotNull();
        assertThat(attributes4.get("value")).isEqualTo(asList("sub","composedTransactional2"));

        MultiValueMap<String, Object> attributes5 = getAllAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalClass3.class,TX_NAME);
        assertThat(attributes5).isNotNull();
        assertThat(attributes5.get("value")).isEqualTo(asList("composedTransactional2","composedTransactional1"));

        MultiValueMap<String, Object> attributes6 = getAllAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalClass4.class,TX_NAME);
        assertThat(attributes6).isNotNull();
        assertThat(attributes6.get("value")).isEqualTo(asList("composedTransactional1","composedTransactional2"));
    }
    /**
     * 当{@link AnnotatedElement}用来表示Class类时， 如果指定类存在父类，且指定的注解在类和父类中存在多次修饰，其中直接注解和元注解在如下情况覆盖
     *   指定类的直接注解和父类直接注解
     */
    @Test
    public void getAllAnnotationAttributesOnMultipleAnnotatedIncludeClassAndSuperClass2() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.SubTransactionalClass3.class,TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("value")).isEqualTo(asList("sub"));
    }


    /**
     * getAllAnnotationAttributes 当{@link AnnotatedElement}用来表示Class类时， 方法会获取该Class父类上标注了@Inherited元注解的注解的元注解
     */
    @Test
    public void getAllAnnotationAttributesOnMultipleAnnotatedIncludeClassAndSuperClass3() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalInheritedClass1.class,TX_NAME);
        assertThat(attributes).isNull();

        MultiValueMap<String, Object> attributes2 = getAllAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalInheritedClass2.class,TX_NAME);
        assertThat(attributes2).isNotNull();
        assertThat(attributes2.get("value")).isEqualTo(asList("composedTransactionalInherited"));
    }

    /**
     * getAllAnnotationAttributes 当{@link AnnotatedElement}用来表示Class类时， 不包含了实现接口的注解
     */
    @Test
    public void getAllAnnotationAttributesOnClassNOIncludeInterfaceAnnotated() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.implementComposedTransactionalInterfaceClass.class,TX_NAME);
        assertThat(attributes).isNull();
    }


    /**
     * getAllAnnotationAttributes {@link AnnotatedElement}用来表示接口时，包含了接口的注解属性
     */
    @Test
    public void isAnnotatedOnInterfaceDirectAnnotated() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.ComposedTransactionalInterface.class, AnnotatedModel2.ComposedTransactional1.class.getName());
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("value")).isEqualTo(asList("composedTransactional1"));

        MultiValueMap<String, Object> attributes2 = getAllAnnotationAttributes(AnnotatedModel2.ComposedTransactionalInterface.class,AnnotatedModel2.ComposedTransactional2.class.getName());
        assertThat(attributes2).isNotNull();
        assertThat(attributes2.get("value")).isEqualTo(asList("composedTransactional2"));
    }

    /**
     * isAnnotated {@link AnnotatedElement}用来表示接口时，包含了标注在接口上的注解的元注解属性
     */
    @Test
    public void isAnnotatedOnInterfaceNotIncludedMetaAnnotation() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.ComposedTransactionalInterface.class, TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("value")).isEqualTo(asList("composedTransactional1","composedTransactional2"));
    }

    /**
     * getAllAnnotationAttributes {@link AnnotatedElement}用来表示接口时，不包含了父接口上的注解,自然不包含其属性
     */
    @Test
    public void isAnnotatedOnInterfaceNotIncludeSuperInterfaceAnnotated() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalInterface.class, TX_NAME);
        assertThat(attributes).isNull();
        MultiValueMap<String, Object> attributes2 = getAllAnnotationAttributes(AnnotatedModel2.SubComposedTransactionalInterface.class, TX_NAME);
        assertThat(attributes2).isNull();
    }
}
