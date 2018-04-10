//静态工厂类
package com.spring.ioc.model;



public class DependencyInjectByStaticFactory {
	
	public static HelloApi newInstance(String message, int index) {
		return new HelloImpl3(message, index);
	}
	
}
