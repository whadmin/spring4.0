package com.spring.ioc.javaConig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.ioc.model.HelloApi;
import com.spring.ioc.model.HelloApi2;
import com.spring.ioc.model.HelloImpl;
import com.spring.ioc.model.HelloImpl2;

@Configuration
public class GetBeanContainer {

    
    /**
     * 返回类型:为bean实现的接口
     * 方法名在没有定义name为Id
     * 当定义name只有一个 name即是id也是name
     * 当定义name有多个 第一个name即是id也是name，后面是别名
     * @return
     */
    @Bean
    public HelloApi2 auto() {
        return new HelloImpl2();
    }

    @Bean
    public HelloApi bean1() {
        return new HelloImpl();
    }

    @Bean(name = { "bean2" })
    public HelloApi bean2() {
        return new HelloImpl();
    }

    @Bean(name = { "bean3","bean3_alias1"})
    public HelloApi bean3() {
        return new HelloImpl();
    }

    @Bean(name = { "bean4" })
    public HelloApi bean4() {
        return new HelloImpl();
    }

    @Bean(name = { "bean5","bean5_alias11", "bean5_alias12", "bean5_alias13", "bean5_alias14" })
    public HelloApi bean5() {
        return new HelloImpl();
    }

}
