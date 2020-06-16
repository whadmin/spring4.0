package com.spring.ioc.bean.assemblyBean.javaConfig;

import com.spring.ioc.bean.assemblyBean.beanObject.no_annotation.BeanObject;
import com.spring.ioc.bean.assemblyBean.beanObject.no_annotation.BeanObject2;
import com.spring.ioc.bean.assemblyBean.beanObject.no_annotation.BeanObject3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @Author: wuhao.w
 * @Date: 2020/6/5 16:12
 */
@Configuration(value ="configurationClassAnnBeanConfig3")
@Order(3)
public class ConfigurationClassAnnBeanConfig3 {

    @Bean(name = "bean3")
    public BeanObject3 beanObject3(BeanObject bean1, BeanObject2 bean2) {
        System.out.println(bean1);
        System.out.println(bean2);
        return new BeanObject3();
    }
}
