package com.j2se.type;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

public class TypeTest<K extends Object & Serializable, V extends Object & Serializable> {
    Map<String, String> map;
    private ClassType classType1;
    private ClassType[] classType2;
    private Genericity<String, String> parameterizedType1;
    private Map<String, List<Genericity<String, String>>> parameterizedType2;
    private Genericity<String, String>[] genericArrayType;
    private Genericity<? extends Number, ? extends Number> wildcardType;
    private TypeTest<Integer, Integer> typeVariable;
    private K key;
    private V value;

    private List<? extends Number> wildcardType1;
    private List<? super String> wildcardType2;


    public static class Genericity<K, V> {
        private K key;
        private V value;
    }

    public static class ClassType {
    }


    @Test
    public void typeTest() throws Exception {

        Field classType1 = TypeTest.class.getDeclaredField("classType1");
        System.out.println(classType1.getGenericType());
        System.out.println(classType1.getGenericType() instanceof Class);

        Field classType2 = TypeTest.class.getDeclaredField("classType2");
        System.out.println(classType2.getGenericType());
        System.out.println(classType2.getGenericType() instanceof Class);

        Field parameterizedType1 = TypeTest.class.getDeclaredField("parameterizedType1");
        System.out.println(parameterizedType1.getGenericType());
        System.out.println(parameterizedType1.getGenericType() instanceof ParameterizedType);

        Field parameterizedType2 = TypeTest.class.getDeclaredField("parameterizedType2");
        System.out.println(parameterizedType2.getGenericType());
        System.out.println(parameterizedType2.getGenericType() instanceof ParameterizedType);

        Field genericArrayType = TypeTest.class.getDeclaredField("genericArrayType");
        System.out.println(genericArrayType.getGenericType());
        System.out.println(genericArrayType.getGenericType() instanceof GenericArrayType);

        Field wildcardType = TypeTest.class.getDeclaredField("wildcardType");
        System.out.println(wildcardType.getGenericType());
        System.out.println(wildcardType.getGenericType() instanceof ParameterizedType);
        System.out.println(((ParameterizedType) wildcardType.getGenericType()).getActualTypeArguments()[0] instanceof WildcardType);
        System.out.println(((ParameterizedType) wildcardType.getGenericType()).getActualTypeArguments()[1] instanceof WildcardType);

        Field typeVariable1 = TypeTest.class.getDeclaredField("key");
        System.out.println(typeVariable1.getGenericType());
        System.out.println(typeVariable1.getGenericType() instanceof TypeVariable);

        Field typeVariable2 = TypeTest.class.getDeclaredField("value");
        System.out.println(typeVariable2.getGenericType());
        System.out.println(typeVariable2.getGenericType() instanceof TypeVariable);
    }


    @Test
    public void parameterizedTypeTest1() throws Exception {
        Field f = TypeTest.class.getDeclaredField("parameterizedType1");
        ParameterizedType pType = (ParameterizedType) f.getGenericType();
        System.out.println(pType instanceof ParameterizedType);  // true
        System.out.println(pType.getRawType());   // Genericity
        for (Type type : pType.getActualTypeArguments()) {
            System.out.println(type);             // 打印两遍: class java.lang.String
        }
        System.out.println(pType.getOwnerType()); // null
    }

    @Test
    public void parameterizedTypeTest2() throws Exception {
        Field f = TypeTest.class.getDeclaredField("parameterizedType2");
        ParameterizedType pType = (ParameterizedType) f.getGenericType();
        System.out.println(pType instanceof ParameterizedType);  // true
        System.out.println(pType.getRawType());   // Map


        Type[] actualTypeArguments = pType.getActualTypeArguments();
        System.out.println(actualTypeArguments[0]);
        System.out.println(actualTypeArguments[0] instanceof Class);

        System.out.println(actualTypeArguments[1]);
        System.out.println(actualTypeArguments[1] instanceof ParameterizedType);

        Type[] actualTypeArguments1 = ((ParameterizedType) actualTypeArguments[1]).getActualTypeArguments();
        System.out.println(actualTypeArguments1[0]);
        System.out.println(actualTypeArguments1[0] instanceof ParameterizedType);


        Type[] actualTypeArguments2 = ((ParameterizedType) actualTypeArguments1[0]).getActualTypeArguments();
        System.out.println(actualTypeArguments2[0]);
        System.out.println(actualTypeArguments2[0] instanceof Class);

        System.out.println(actualTypeArguments2[1]);
        System.out.println(actualTypeArguments2[1] instanceof Class);
    }


    @Test
    public void wildcardType1Test() throws Exception {
        Field fieldA = TypeTest.class.getDeclaredField("wildcardType1");
        Field fieldB = TypeTest.class.getDeclaredField("wildcardType2");
        // 先拿到范型类型
        System.out.println(fieldA.getGenericType() instanceof ParameterizedType);
        System.out.println(fieldB.getGenericType() instanceof ParameterizedType);
        ParameterizedType pTypeA = (ParameterizedType) fieldA.getGenericType();
        ParameterizedType pTypeB = (ParameterizedType) fieldB.getGenericType();
        // 再从范型里拿到通配符类型
        System.out.println(pTypeA.getActualTypeArguments()[0] instanceof WildcardType);
        System.out.println(pTypeB.getActualTypeArguments()[0] instanceof WildcardType);

        WildcardType wTypeA = (WildcardType) pTypeA.getActualTypeArguments()[0];
        WildcardType wTypeB = (WildcardType) pTypeB.getActualTypeArguments()[0];
        // 方法测试
        System.out.println(wTypeA.getUpperBounds()[0]);   // class java.lang.Number
        System.out.println(wTypeB.getLowerBounds()[0]);   // class java.lang.String
        // 看看通配符类型到底是什么, 打印结果为: ? extends java.lang.Number
        System.out.println(wTypeA);
    }

    @Test
    public void genericArrayTypeTest() throws Exception {
        Field field = TypeTest.class.getDeclaredField("genericArrayType");
        System.out.println(field.getGenericType());
        System.out.println(field.getGenericType() instanceof GenericArrayType);

        GenericArrayType genericArrayType = (GenericArrayType) field.getGenericType();
        Type genericComponentType = genericArrayType.getGenericComponentType();
        System.out.println(genericComponentType);
        System.out.println(genericComponentType instanceof ParameterizedType);
    }


    @Test
    public void typeVariableTest() throws Exception {
        // 获取字段的类型
        Field fk = TypeTest.class.getDeclaredField("key");
        Field fv = TypeTest.class.getDeclaredField("value");
        TypeVariable keyType = (TypeVariable) fk.getGenericType();
        TypeVariable valueType = (TypeVariable) fv.getGenericType();
        // getName 方法
        System.out.println(keyType.getName());                 // K
        System.out.println(valueType.getName());               // V
        // getGenericDeclaration 方法
        System.out.println(keyType.getGenericDeclaration());   // class com.test.TestType
        System.out.println(valueType.getGenericDeclaration()); // class com.test.TestType
        // getBounds 方法
        System.out.println("K 的上界:");                        // 有两个
        for (Type type : keyType.getBounds()) {
            System.out.println(type);
        }

        System.out.println("V 的上界:");                        // 有两个
        for (Type type : valueType.getBounds()) {
            System.out.println(type);
        }
    }

}
