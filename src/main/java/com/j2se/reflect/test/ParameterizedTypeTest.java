package com.j2se.reflect.test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.j2se.reflect.model.Person;

public class ParameterizedTypeTest {

	@Test
	public void test() throws Exception{
		Method method = Person.class.getMethod("ParameterizedType",List.class,Map.class);
		Type[] parameterTypes=method.getGenericParameterTypes();
		System.out.println(parameterTypes.length);
		for(int i=0;i<parameterTypes.length;i++){
			if (parameterTypes[i] instanceof ParameterizedType) {
				System.out.println(parameterTypes[i]);
				System.out.println(((ParameterizedType) parameterTypes[i]).getActualTypeArguments()[0]);
				System.out.println("---------------");
			}

			
		}
		Type returnType=method.getGenericReturnType();
		System.out.println(returnType);
		
		Map<Integer, String> maps = new HashMap<>();
		ParameterizedType pType = (ParameterizedType) maps.getClass().getGenericSuperclass();//获得HashMap的父类
		System.out.println(pType.getRawType());//class java.util.AbstractMap
		if(pType.getRawType() instanceof Class){
		      System.out.println("true");//true
		}
		
		  method = Person.class.getMethod("applyMethod",Map.Entry.class);
		 Type[] types = method.getGenericParameterTypes();
		  pType = (ParameterizedType)types[0];
		 //返回所有者类型，打印结果是interface java.util.Map
		System.out.println(pType.getOwnerType());
	}

}
