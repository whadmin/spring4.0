package com.spring.common.proxy.Service.impl;


/**
 * HelloServiceImpl
 * description
 * create by lxj 2018/4/27
 **/
public class HelloServiceImpl extends HelloServiceBase implements IHelloService {

    @Override
    public void sayHello(String name) {
        System.out.println("hello: " + name);
    }

    @Override
    public void sayBye(String name) {
        System.out.println("bye :" + name);
    }

    @Override
    public void sayHello1(String name) {

    }

    @Override
    public void sayBye1(String name) {

    }
}
