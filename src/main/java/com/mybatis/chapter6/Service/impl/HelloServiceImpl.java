package com.mybatis.chapter6.Service.impl;

import com.mybatis.chapter6.Service.HelloService;


/**
 * HelloServiceImpl
 * description
 * create by lxj 2018/4/27
 **/
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String name) {
        System.out.println("hello: " + name);
    }

    @Override
    public void sayBye(String name) {
        System.out.println("bye :" + name);
    }
}
