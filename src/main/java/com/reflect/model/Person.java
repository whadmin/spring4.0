package com.reflect.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.annotation.model.MyClass;

@MyClass
public class Person extends Father implements InterFace{
	private String name;
	private int age;
	private String msg = "hello wrold";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Person() {
	}

	private Person(String name) {
		this.name = name;
		System.out.println(name);
	}

	public void fun() {
		System.out.println("fun");
	}

	public void fun(String name, int age) {
		System.out.println("我叫" + name + ",今年" + age + "岁");
	}
	
	public List<String> ParameterizedType(List<Date> list,Map<String, String> map){
		return null;
	}
	
	public static <T,U> void applyMethod(Map.Entry<T,U> mapEntry){
	}
}