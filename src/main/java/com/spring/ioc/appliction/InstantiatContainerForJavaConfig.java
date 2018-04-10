package com.spring.ioc.appliction;


import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.spring.ioc.javaConig.InstantiatContainer;
import com.spring.ioc.model.HelloApi;

public class InstantiatContainerForJavaConfig {

    @Test
    public void testJavaConfigSystemApplicationContext() {
        // 1.准备spring Java配置文件
        // 2.初始化容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InstantiatContainer.class);
        // 3、从容器中获取Bean
        HelloApi helloApi = context.getBean("hello", HelloApi.class);
        // 4、执行业务逻辑
        helloApi.sayHello();
    }
}
