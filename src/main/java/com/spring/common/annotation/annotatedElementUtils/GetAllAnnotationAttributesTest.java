package com.spring.common.annotation.annotatedElementUtils;

import com.spring.common.annotation.AnnotatedModel2;
import org.junit.Test;
import org.springframework.util.MultiValueMap;

import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.core.annotation.AnnotatedElementUtils.getAllAnnotationAttributes;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/24 15:36
 */
public class GetAllAnnotationAttributesTest {

    private static final String TX_NAME = AnnotatedModel2.Transactional2.class.getName();

    private Set<String> names(Class<?>... classes) {
        return stream(classes).map(Class::getName).collect(toSet());
    }


    /**
     * 获取注解属性Map集合，指定{@link AnnotatedElement}，以及修饰注解
     *
     * 如果{@link AnnotatedElement}类型为Class
     *
     * 1 getAllAnnotationAttributes 方法指定会获取{@link AnnotatedElement}被修饰的注解

     *     1  Class上的修饰的注解(及其元注解)
     *     2  Class祖先类Class上的修饰的注解(及其元注解)【这里修饰祖先类的注解必须@Inherited】
     *
     * 2 判断指定指定{@link AnnotatedElement}是否被指定注解多次修饰
     *
     *     1 如果指定注解在当前类或父类上同时修饰,且注解类型相同（同样是注解修饰注解或同样是元注解），对注解进行合并，属性值取交集，子类优先级大于父类。
     *
     *     2 如果指定注解作为当前类元注解进行多次修饰，对注解进行合并，属性值取并集。
     */
    @Test
    public void getAllAnnotationAttributesTest(){
        //如果指定注解在当前类或父类上同时修饰,且注解类型相同（同样是注解修饰注解或同样是元注解），对注解进行合并，属性值取交集，子类优先级大于父类。
        getAllAnnotationAttributesMultipleAnnotationRewrite();
        //如果指定注解作为当前类元注解进行多次修饰，对注解进行合并，属性值取并集。
        getAllAnnotationAttributesMultipleAnnotationComposed();
    }


    @Test
    public void getAllAnnotationAttributesMultipleAnnotationRewrite() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.SubMultipleAnnotationAttrRewrite.class,TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("qualifier")).isEqualTo(asList("DerivedTxConfig"));

        MultiValueMap<String, Object> attributes2 = getAllAnnotationAttributes(AnnotatedModel2.SubMultipleAnnotationAttrRewrite2.class,TX_NAME);
        assertThat(attributes2).isNotNull();
        assertThat(attributes2.get("qualifier")).isEqualTo(asList("DerivedTxConfig"));

        MultiValueMap<String, Object> attributes3 = getAllAnnotationAttributes(AnnotatedModel2.SubMultipleAnnotationAttrRewrite3.class,TX_NAME);
        assertThat(attributes3).isNotNull();
        assertThat(attributes3.get("qualifier")).isEqualTo(asList("DerivedTxConfig"));
    }


    @Test
    public void getAllAnnotationAttributesMultipleAnnotationComposed() {
        MultiValueMap<String, Object> attributes = getAllAnnotationAttributes(AnnotatedModel2.MultipleAnnotationAttrComposed1.class, TX_NAME);
        assertThat(attributes).isNotNull();
        assertThat(attributes.get("qualifier")).isEqualTo(asList("TxConfig", "DerivedTxConfig"));

        MultiValueMap<String, Object> attributes2 = getAllAnnotationAttributes(AnnotatedModel2.MultipleAnnotationAttrComposed2.class, TX_NAME);
        assertThat(attributes2).isNotNull();
        assertThat(attributes2.get("qualifier")).isEqualTo(asList("DerivedTxConfig", "TxConfig"));
    }

}
