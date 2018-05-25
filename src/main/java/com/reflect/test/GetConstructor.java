package com.reflect.test;

import java.lang.reflect.Constructor;

public class GetConstructor {

	   public static void main(String[] args) {

	        try {
	            //创建类
	            Class<?> class1 = Class.forName("com.reflect.model.Person");

	            //获取所有的构造函数
	            Constructor<?>[] constructors = class1.getConstructors() ;

	            for (Constructor<?> constructor : constructors) {
	                System.out.println( constructor );
	            }


	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }

}
