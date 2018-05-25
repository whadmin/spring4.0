package com.type.model;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;

public class WildcardTypeBean {
    private List<? extends Number> a;  // a没有下界,
//  没有指定的话，上边界默认是 Object ,下边界是  String                      
    private List<? super String> b; 

    private List<String> c;

    private Class<?> aClass;
    
    public static void testWildCardType() {
        try {
            Field[] fields = WildcardTypeBean.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                Type type = field.getGenericType();
                String nameString = field.getName();
                System.out.println("下面开始打印" + nameString + "是否具有通配符");
                if (!(type instanceof ParameterizedType)) {
                    System.out.println("---------------------------");
                    continue;
                }
                ParameterizedType parameterizedType = (ParameterizedType) type;
                type = parameterizedType.getActualTypeArguments()[0];
                if (!(type instanceof WildcardType)) {
                    System.out.println("---------------------------");
                    continue;
                }
                WildcardType wildcardType = (WildcardType) type;
                Type[] lowerTypes = wildcardType.getLowerBounds();
                if (lowerTypes != null) {
                    System.out.println("下边界");
                    System.out.println(lowerTypes);
                }
                Type[] upTypes = wildcardType.getUpperBounds();
                if (upTypes != null) {
                    System.out.println("上边界");
                    System.out.println(upTypes);
                }
                System.out.println("---------------------------");

            }
            Field fieldA = WildcardTypeBean.class.getDeclaredField("a");
            Field fieldB = WildcardTypeBean.class.getDeclaredField("b");
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
            System.out.println(wTypeA.getUpperBounds()[0]); 
            System.out.println(wTypeB.getLowerBounds()[0]); 
            // 看看通配符类型到底是什么, 打印结果为: ? extends java.lang.Number
            System.out.println(wTypeA);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public static void main(String[] args) {
    	WildcardTypeBean.testWildCardType();
	}
}