package com.spring.common.proxy.Service.impl;

import com.spring.common.proxy.Service.impl.HelloService;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/21 17:57
 */
public interface IHelloService extends HelloService {

    public void sayHello1(String name);

    public void sayBye1(String name);

}
