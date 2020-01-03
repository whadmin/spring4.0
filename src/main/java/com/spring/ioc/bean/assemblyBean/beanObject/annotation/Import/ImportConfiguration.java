package com.spring.ioc.bean.assemblyBean.beanObject.annotation.Import;

import com.spring.ioc.bean.assemblyBean.beanObject.no_annotation.BeanObject2;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: wuhao.w
 * @Date: 2020/1/1 22:08
 */
@Configuration(value ="importConfiguration")
public class ImportConfiguration {

    @Bean(name = "bean2",autowire = Autowire.NO, destroyMethod ="destroy", initMethod ="init")
    public BeanObject2 BeanObject2() {
        return new BeanObject2();
    }
}
