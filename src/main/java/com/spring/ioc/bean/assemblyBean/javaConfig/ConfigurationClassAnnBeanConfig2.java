package com.spring.ioc.bean.assemblyBean.javaConfig;

import com.spring.ioc.bean.assemblyBean.beanObject.no_annotation.BeanObject2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: wuhao.w
 * @Date: 2020/6/5 16:08
 */
@Configuration(value ="configurationClassAnnBeanConfig2")
public class ConfigurationClassAnnBeanConfig2 {

    @Bean(name = "bean2", destroyMethod ="destroy", initMethod ="init")
    public BeanObject2 beanObject2() {
        return new BeanObject2();
    }

}
