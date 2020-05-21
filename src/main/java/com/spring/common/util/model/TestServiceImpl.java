package com.spring.common.util.model;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/20 20:27
 */
public class TestServiceImpl implements ITestService {

    public void sayHello(String name) {
        System.out.println("hello: " + name);
    }


}
