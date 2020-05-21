package com.spring.common.util.model;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/20 20:10
 */
public interface TestService {

     void sayHello(String name);

     default void test() {
    }
}
