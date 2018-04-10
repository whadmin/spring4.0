//实例工厂类
package com.spring.ioc.model;



public class DependencyInjectByInstanceFactory {
    
    public HelloApi newInstance(String message, int index) {
        return new HelloImpl3(message, index);
    }
    
}
