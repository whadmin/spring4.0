package com.spring.common.resolvableType;
import org.junit.Test;
import org.springframework.core.ResolvableType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class ResolvableTypeTests {

    @Test
    public void forClass1() throws Exception {
        /** 获取指定Class对应ResolvableType，ResolvableTypeTests.ExtendsList.class Java Type类型为普通类型 **/
        ResolvableType type = ResolvableType.forClass(ResolvableTypeTests.ExtendsList.class);
        /** 获取Java Type类型 ResolvableTypeTests.ExtendsList.class 类型为普通类型Class **/
        assertThat(type.getType()).isEqualTo(ResolvableTypeTests.ExtendsList.class);
        /** 如果当前对象类型为普通类型或ParameterizedType 返回被修饰的Class对象，否则返回null **/
        assertThat(type.getRawClass()).isEqualTo(ResolvableTypeTests.ExtendsList.class);

        /** 如果ResolvableType被泛型修饰，返回修饰ResolvableType数组，由于是当前是普通类型，没有可用的泛型，则返回一个空数组 **/
        assertThat(type.getGenerics().length).isEqualTo(0);

        /** 返回被泛型修饰ResolvableType数组，指定下标ResolvableType对象，如果不存在不会抛异常，而是返回ResolvableType.NONE **/
        assertThat(type.getGeneric(0)).isEqualTo(ResolvableType.NONE);
        assertThat(type.getGeneric(1)).isEqualTo(ResolvableType.NONE);

        /** 判断当前ResolvableType 是否被泛型修饰 **/
        assertThat(type.hasGenerics()).isEqualTo(false);

        /** 返回被修饰的Class对象**/
        assertThat(type.resolve()).isEqualTo(ResolvableTypeTests.ExtendsList.class);
    }

    @Test
    public void forClass2() throws Exception {
        /** 获取指定Class对应ResolvableType，ResolvableTypeTests.ExtendsList.class Java Type类型为普通类型 **/
        ResolvableType type = ResolvableType.forClass(ResolvableTypeTests.ExtendsList.class);

        /** 获取当前ResolvableType父类对应ResolvableType **/
        ResolvableType superType= type.getSuperType();

        /** 获取Java Type类型 ArrayList<CharSequence> 类型为其ParameterizedType **/
        ParameterizedType parameterizedType = (ParameterizedType)superType.getType();
        assertThat(parameterizedType.getRawType()).isEqualTo((Type)ArrayList.class);
        for (Type subType : parameterizedType.getActualTypeArguments()) {
            assertThat(subType instanceof Class);
            assertThat(subType).isEqualTo(CharSequence.class);
        }

        /** 如果当前对象类型为普通类型或ParameterizedType 返回被修饰的Class对象，否则返回null **/
        assertThat(superType.getRawClass()).isEqualTo(ArrayList.class);
        assertThat(superType.hasGenerics()).isEqualTo(true);
        assertThat(superType.resolve()).isEqualTo(ArrayList.class);


    }

    @Test
    public void forClass3() throws Exception {
        /** 获取指定Class对应ResolvableType，ResolvableTypeTests.ExtendsList.class Java Type类型为普通类型 **/
        ResolvableType type = ResolvableType.forClass(ResolvableTypeTests.ExtendsList.class);

        /** 获取当前ResolvableType父类对应ResolvableType **/
        ResolvableType superType= type.getSuperType();

        /** 如果ResolvableType被泛型修饰，返回修饰ResolvableType数组，由于ArrayList<CharSequence>类型为ParameterizedType，存在一个修饰泛型ResolvableType **/
        assertThat(superType.getGenerics().length).isEqualTo(1);
        /** 获取修饰superType第一个泛型对应的 ResolvableType 对象，这里用来表示 CharSequence，是一个普通类型**/
        assertThat(superType.getGeneric(0).hasGenerics()).isEqualTo(false);

        /** 获取Java Type类型 CharSequence.class 类型为普通类型Class **/
        assertThat(superType.getGeneric(0).getType()).isEqualTo(CharSequence.class);
        /** 如果当前对象类型为普通类型或ParameterizedType 返回被修饰的Class对象，否则返回null，这里为普通类型返回对应Class **/
        assertThat(superType.getGeneric(0).getRawClass()).isEqualTo(CharSequence.class);
        /** 返回被修饰的Class对象 **/
        assertThat(superType.getGeneric(0).resolve()).isEqualTo(CharSequence.class);
        /** 等于superType.getGeneric(0).resolve() **/
        assertThat(superType.resolveGeneric(0)).isEqualTo(CharSequence.class);
        /** 返回superType.getGenerics()返回所有被修饰的Class对象 **/
        assertThat(superType.resolveGenerics().length).isEqualTo(1);
    }

    static class ExtendsList extends  ArrayList {

    }

}
