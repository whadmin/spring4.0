package com.spring.common.util;

import com.spring.common.proxy.Service.impl.HelloService;
import com.spring.common.proxy.Service.impl.HelloServiceImpl;
import com.spring.common.proxy.Service.proxy.HelloServiceCgLib;
import org.springframework.aop.support.AopUtils;

/**
 * @Author: wuhao.w
 * @Date: 2020/5/19 21:18
 */
public class AopUtilsTest {



    public static void main(String[] args) throws NoSuchMethodException {
        HelloServiceCgLib helloServiceCgLib = new HelloServiceCgLib();
        HelloService target = (HelloService) helloServiceCgLib.getTarget(new HelloServiceImpl());
       System.out.println(target.getClass());
       System.out.println(AopUtils.getTargetClass(target));
    }
}
