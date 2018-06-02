package com.j2se.type.model;

import static java.lang.System.out;  

import java.lang.reflect.Method;  
import java.lang.reflect.ParameterizedType;  
import java.lang.reflect.Type;  
import java.lang.reflect.TypeVariable;  
import java.util.ArrayList;  
import java.util.Date;  
import java.util.List;  
public class GenericParamType {  
      
    // 些功能是演示得到泛型类中的参数变量的类型  
    public static void main(String args[]) throws Exception  
    {  
        /* type 是所有类型的高级公共接口，当然也是Class　的父类 
         * 它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。 
         
          先来看一下　Type 的用法 
          type 是一种表示编程语言中的所有类型的类超级接口，如果　int Integer String 
          这都是表示一编程语言的类型，而其中的 int.class Integer.class String.class 它们表示的是类型的实例 
          如果，我们前面学习的反射 Class c = Integer.class Class 相当于表示类型的类,而Integer.class 则是一种 
          名为整形类型的类型实例　 
          理解了上面的那些，其　理解 type 就不难了，type 与 class 一样，不过 type 是一种比　Class 表示范围不要广的 
          超级接口，它表示Java语言中类型的所有接口 
        */  
          
        //typeTest();  
        //而对于　Type，其实现了 CenericArrayType 接口  
        List list = new ArrayList<Date>();  
        //convert();  
        typeVariable(list);  
        //applyMethod(list);  
        //relations();  
    //  int[] array = new int[] {1,2,3};  
        //genericArrayTypeTest(array);  
    }  
      
    public static void applyMethod(List<Date> list) throws Exception  
    {  
        Method m = GenericParamType.class.getMethod("applyMethod",List.class);  
        Type[] t1 = m.getParameterTypes();// 其返回是参数的类型  
        Type[] t2 = m.getGenericParameterTypes();//其返回的是参数的参数化的类型,里面的带有实际的参数类型  
        Method m2 = GenericParamType.class.getMethod("main",String[].class);  
        Type[] t3 = m2.getGenericParameterTypes();//参数里面如果不是参数化类型的话，那么 getGenericParameterTypes就返回与 getParameterTypes 一样　  
        Type[] t4 = m2.getParameterTypes();  
        out.println(t1[0]);//interface java.util.List    
        out.println(t2[0]);//java.util.List<java.util.Date>  
        //我们通过　getGenericParameterTypes 得到的是 List<Date>，那么我们怎么能得到它的参数化类型的实例呢  
        // type 还有好多子接口，我们通过子接口来操作  
        out.println(t2.getClass());  
        ParameterizedType t = (ParameterizedType)t2[0];//将类型向参数化类型转换  
        out.println(t.getClass());  
        out.println(t.getActualTypeArguments()[0]);// 可以得到参数化类型的参数实例  
          
    }  
  
    public static void convert()  
    {  
        List<String> list = new ArrayList<String>();  
        Type type = list.getClass().getGenericSuperclass();  
        out.println(type);  
        ParameterizedType pt = (ParameterizedType)type;  
        out.println(pt.getActualTypeArguments()[0]);  
          
        Integer array[] = new Integer[10];  
        out.println(array.getClass().isArray());  
        Type t1 = array.getClass().getGenericSuperclass();  
        out.println(t1);  
    //  GenericArrayType pt2 = (GenericArrayType)t1;  
    }  
    public static void relations()  
    {  
        Type types = Class.class.getGenericSuperclass();  
        out.println(types);  
    }  
      
      
      
    public static void typeTest()  
    {  
        Type t1 = Integer.class;  
        out.println(t1);  
        Type t2 = String.class;  
        out.println(t2);  
        Type t3 = int[].class;  
        out.println(t3);  
        Type t4 = int[][].class;  
        out.println(t4);  
        Type t5 = Class.class;  
        out.println(t5);  
        Class t6 = Class.class;  
        out.print(t6);  
    }  
    public static void typeVariable(List<Date> list)  
    {  
        out.println(list.getClass());  
        TypeVariable tval[] = list.getClass().getTypeParameters();  
        TypeVariable v = tval[0];  
        Type t[] = v.getBounds();  
        out.println(t.length);  
        //out.println(v.getGenericDeclaration().getTypeParameters()[0]);  
          
        /*out.println(tval.length); 
        for(TypeVariable val:tval) 
            out.println(val);*/  
    }  
      
      
}  
