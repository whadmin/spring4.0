package com.spring.ioc.bean.assemblyBean.javaConfig;

import com.spring.ioc.bean.assemblyBean.beanObject.no_annotation.BeanObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @Author: wuhao.w
 * @Date: 2020/6/5 14:33
 */
@Configuration(value ="configurationClassAnnBeanConfig1")
@Order(1)
public class ConfigurationClassAnnBeanConfig1 {

    @Bean(name = "bean1", destroyMethod ="destroy", initMethod ="init")
    public BeanObject beanObject() {
        return new BeanObject();
    }


}
