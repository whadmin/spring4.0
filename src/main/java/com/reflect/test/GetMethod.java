package com.reflect.test;

import java.lang.reflect.Method;

public class GetMethod {
	public static void main(String[] args) {

		try {
			// 创建类
			Class<?> class1 = Class.forName("com.reflect.model.Person");

			// 获取所有的公共的方法
			Method[] methods = class1.getMethods();

			for (Method method : methods) {
				System.out.println(method);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
