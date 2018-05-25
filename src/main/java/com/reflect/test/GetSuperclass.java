package com.reflect.test;

public class GetSuperclass {

	public static void main(String[] args) {

        try {
            //创建类
            Class<?> class1 = Class.forName("com.reflect.model.Person");

            //获取父类
            Class<?> superclass = class1.getSuperclass() ;

            System.out.println( superclass );

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
