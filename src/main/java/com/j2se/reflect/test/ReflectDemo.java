package com.j2se.reflect.test;

import com.j2se.reflect.model.MyClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;



public class ReflectDemo {
	public static void main(String[] args) {
		try {
			Class c = Class.forName("com.reflect.model.Person");
			Object o = c.newInstance();
			Method method = c.getMethod("fun", String.class, int.class);
			
			
			
			method.getAnnotatedParameterTypes();
			method.invoke(o, "tengj", 10);

			Method[] methods = c.getDeclaredMethods();
			for (Method m : methods) {
				String methodName = m.getName();
				System.out.println(methodName);
			}
			System.out.println("----------------");
			methods = c.getMethods();
			for (Method m : methods) {
				String methodName = m.getName();
				System.out.println(methodName);
			}

			System.out.println("----------------");
			Field[] fields = c.getDeclaredFields();
			for (Field field : fields) {
				System.out.println(field.getName());
			}

			System.out.println("----------------");
			// 获取构造函数
			Constructor constructor = c.getDeclaredConstructor(String.class);
			constructor.setAccessible(true);// 设置是否允许访问，因为该构造器是private的，所以要手动设置允许访问，如果构造器是public的就不需要这行了。
			constructor.newInstance("tengj");
			
			Constructor[] constructors = c.getDeclaredConstructors();
            for(Constructor constructorz:constructors){
                System.out.println(constructorz);
            }
            
            System.out.println("----------------");
            Annotation[] annotations = (Annotation[]) c.getAnnotations();//获取class对象的所有注解 
            Annotation annotation = (Annotation) c.getAnnotation(MyClass.class);//获取class对象指定注解
            Type genericSuperclass = c.getGenericSuperclass();//获取class对象的直接超类的 
            Type[] interfaceTypes = c.getGenericInterfaces();//获取class对象的所有接口的type集合
            
            System.out.println(annotations);
            System.out.println(annotation);
            System.out.println(genericSuperclass);
            System.out.println(interfaceTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}