package com.j2se.reflect.test;

import java.lang.reflect.Field;

public class GetDeclaredField {
	public static void main(String[] args) {

        try {
            //创建类
            Class<?> class1 = Class.forName("com.reflect.model.Person");

            //获得所有的字段属性：包括 
            Field[] declaredFields = class1.getDeclaredFields() ;

            Field[] fields = class1.getFields() ;

            for( Field field : declaredFields ){
                System.out.println( "de--  " +  field  );
            }

            for( Field field : fields ){
                System.out.println( "fields--  " +  field  );
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
